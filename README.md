# Your Personal Email Assistant

This repository contains the backend server that integrates with the **Qwen-fast API** and **DeepSeek API** as the LLM (Large Language Model) to generate custom email templates based on the provided request body.

---

## Table of Contents
1. [Request Body](#request-body)
2. [Response Body](#response-body)
3. [Example Usage](#example-usage)
4. [About Qwen-fast API](#about-qwen-fast-api)
---

## 1. Request Body

### Structure
The request body should be sent as a JSON object with the following fields:

| Field Name      | Type   | Description                                                             | Example Value                |
|------------------|--------|------------------------------------------------------------------------|------------------------------|
| `emailContent`   | string | The content of the email that needs to be processed.                   | `"coding is happy but hard"` |
| `tone`           | string | The desired tone for the email content (e.g., funny, formal, casual).  | `"funny"`                    |
| `minWordLength`  | number | The minimum word length for the generated content.                     | `20`                         |
| `maxWordLength`  | number | The maximum word length for the generated content.                     | `40`                         |
| `language`       | string | The language in which the content should be generated.                 | `"traditional chinese"`      |

### Example Request Body
```json
{
    "emailContent": "coding is happy but hard",
    "tone": "funny",
    "minWordLength": 20,
    "maxWordLength": 40,
    "language": "traditional chinese"
}
```

## 2. Response Body
The response body contains the generated email content in the specified language and tone.

```
編碼快樂又辛苦呢！不過，誰叫我們這麼熱愛呢？繼續加油吧，反正咖啡因永遠不會背叛我們！ 😊
```
--

## 3. Example Usage

### Request

```
POST /api/generate-email
Content-Type: application/json

{
    "emailContent": "coding is happy but hard",
    "tone": "funny",
    "minWordLength": 20,
    "maxWordLength": 40,
    "language": "traditional chinese"
}
```

### Response

```
HTTP/1.1 200 OK
Content-Type: application/json

編碼快樂又辛苦呢！不過，誰叫我們這麼熱愛呢？繼續加油吧，反正咖啡因永遠不會背叛我們！ 😊

```

--

## 4. About Qwen-fast API

### Overview
The Qwen-fast API is a high-performance Large Language Model (LLM) designed for natural language processing tasks. It is optimized for fast and efficient text generation, making it ideal for applications like email template generation.

### Integration
The backend server sends the request body to the Qwen-fast API, which processes the input and returns the generated email content. The API’s response is then formatted and sent back to the client.

## 5. About DeepSeek API

### Overview
The DeepSeek API is a powerful Large Language Model (LLM) designed for advanced natural language processing tasks. While it operates at a slightly slower speed compared to Qwen-fast, it excels in generating content that better aligns with client needs, making it highly suitable for applications such as email template generation and content creation.

### Integration
The backend server sends the request body to the DeepSeek API, which processes the input and generates high-quality email content. The API’s response is then formatted and delivered back to the client, ensuring seamless integration and optimal content quality. 