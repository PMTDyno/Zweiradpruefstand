# Communication Protocol

## Overview

* **Server / Client principal**  
  Server is the microcontroller, client is the Java application

The client is starting with sending a request frame. The server answers with a response frame.

## Frame Description

### CRC
to do ...

### Abbreviations

* **SOT**: = 2 (ASCII control character *Start of Text*)
* **SN**: = 0 or 1 (Package Sequence Number)
* **ACK**: = 0 or 1  
           0 ... Positive Acknowledge of frame with number *SN*  
           1 ... Negative Acknowledge of frame with number *SN*
* **Dx**: Data in Base64 Encoding, *n* Bytes (x = 0 .. n-1)  
  Example: data `connect` -> Base64 encoded `Y29ubmVjdA==` -> D0='Y', D1='2', D2='9', D3='u', ... D11='='
* **GS**: = 29 (0x1d) (ASCII control character *Group Separator*)
* **CRC**: 16 Bit CRC value `0000` ... `ffff`  
  Excample: CRC value `0xabcd` means ... CRC3='a', CRC2='b', CRC1='c', CRC0='d'
* **EOT**: = 3 (ASCII control character *End of Text*)

### Request (client to server)

     -------------------------------------------------------------------------
    | SOT | SN | D0 | D1 | ... | Dn-1 | GS | CRC3 | CRC2 | CRC1 | CRC0 | EOT |
     -------------------------------------------------------------------------

Minimum size: 8 Bytes  
Maximum size: ?? Bytes

Example: ...

### Response (server to client)

     ------------------------------------------------------------------------------
    | SOT | SN | ACK | D0 | D1 | ... | Dn-1 | GS | CRC3 | CRC2 | CRC1 | CRC0 | EOT |
     ------------------------------------------------------------------------------

Minimum size: 9 Bytes  
Maximum size: ?? Bytes

Example: ...

