import ollama
import json
import base64
import re
import os
from typing import Optional, Dict, Any, List

# ==============================
# Constants
# ==============================
INVOICE_JSON_SCHEMA = """
{
  "invoiceNumber": "string",
  "invoiceDate": "string",
  "dueDate": "string",
  "totalAmount": "number",
  "currency": "string",
  "vendorName": "string",
  "customerName": "string",
  "lineItems": [
    {
      "description": "string",
      "quantity": "number",
      "unitPrice": "number",
      "total": "number"
    }
  ]
}
"""

SYSTEM_PROMPT = f"""
You are a professional invoice data extraction tool. 
Your task is to extract key information from the provided invoice image.

You MUST return the extracted data as a valid JSON object. 
Do NOT include any other text or explanation.

The JSON object should have the following structure:
{INVOICE_JSON_SCHEMA}

If a field is not found, use a null value for that field.
"""

USER_PROMPT = "Extract the invoice details from this image and format the response as a JSON object."


# ==============================
# Functions
# ==============================
def encode_image_to_base64(image_path: str) -> Optional[str]:
    """Encodes an image file to a Base64 string."""
    try:
        with open(image_path, "rb") as image_file:
            return base64.b64encode(image_file.read()).decode("utf-8")
    except FileNotFoundError:
        print(f"[ERROR] File not found: {image_path}")
    except Exception as e:
        print(f"[ERROR] Failed to read image {image_path}: {e}")
    return None


def extract_json_from_text(text: str) -> Optional[Dict[str, Any]]:
    """Extract the first valid JSON object from text using regex."""
    match = re.search(r"\{.*\}", text, re.DOTALL)
    if not match:
        return None
    try:
        return json.loads(match.group(0))
    except json.JSONDecodeError:
        return None


def extract_invoice_data(image_path: str) -> Optional[Dict[str, Any]]:
    """
    Sends an invoice image to a local Ollama model (like LLaVA) for data extraction.
    Returns the parsed JSON dictionary or None if extraction fails.
    """
    base64_image = encode_image_to_base64(image_path)
    if not base64_image:
        return None

    try:
        response = ollama.generate(
            model="llava",  # Ensure the 'llava' multimodal model is available
            prompt=USER_PROMPT,
            images=[base64_image],
            system=SYSTEM_PROMPT,
            stream=False,
        )

        raw_response = response.get("response", "").strip()
        if not raw_response:
            print(f"[ERROR] Empty response for {image_path}")
            return None

        extracted_data = extract_json_from_text(raw_response)
        if not extracted_data:
            print(f"[ERROR] Could not parse JSON for {image_path}")
            print("Raw response:")
            print(raw_response)
            return None

        print(f"[INFO] Successfully extracted data from {image_path}:")
        print(json.dumps(extracted_data, indent=2))
        return extracted_data

    except ConnectionError as e:
        print(f"[ERROR] Connection issue with Ollama: {e}")
    except Exception as e:
        print(f"[ERROR] Unexpected error while processing {image_path}: {e}")

    return None


def find_images_in_folder(folder_path: str) -> List[str]:
    """Return all .png, .jpg, .jpeg files inside the given folder (non-recursive)."""
    supported_ext = {".png", ".jpg", ".jpeg"}
    try:
        return [
            os.path.join(folder_path, f)
            for f in os.listdir(folder_path)
            if os.path.splitext(f.lower())[1] in supported_ext
        ]
    except FileNotFoundError:
        print(f"[ERROR] Folder not found: {folder_path}")
        return []
    except Exception as e:
        print(f"[ERROR] Could not list files in {folder_path}: {e}")
        return []


def process_folder(folder_path: str):
    """Process all supported images in a folder and extract invoice data."""
    image_files = find_images_in_folder(folder_path)
    if not image_files:
        print("[INFO] No images found in folder.")
        return

    all_results = {}
    for img_path in image_files:
        result = extract_invoice_data(img_path)
        if result:
            all_results[img_path] = result

    # Save results to JSON file
    if all_results:
        output_file = os.path.join(folder_path, "invoices_extracted.json")
        with open(output_file, "w", encoding="utf-8") as f:
            json.dump(all_results, f, indent=2, ensure_ascii=False)
        print(f"[INFO] Extraction completed. Results saved to {output_file}")


# ==============================
# Main entry point
# ==============================
if __name__ == "__main__":
    folder_path = "./asset"  # change to your folder
    process_folder(folder_path)

