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
* **ACK**: = 6 or 21 (ASCII control character ACK or NACK)  
           6 ... ACK = Positive Acknowledge of frame with number *SN*  
           21 ... NACK = Negative Acknowledge of frame with number *SN*
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

#### Data Field

* "start": Timestamp ist cleared to zero, and clock for timestamp is starting
* "refresh": send back timestamp, temperature, humidity and airpressure
* "measure": send back timestamp, rotation-shaft and motor-rotation

#### Example

....

### Response (server to client)

     ------------------------------------------------------------------------------
    | SOT | SN | ACK | D0 | D1 | ... | Dn-1 | GS | CRC3 | CRC2 | CRC1 | CRC0 | EOT |
     ------------------------------------------------------------------------------

Minimum size: 9 Bytes  
Maximum size: ?? Bytes


#### Data Field

* on request "start": response equals to request refresh

* on Request "refresh": TIMESTAMP + US TEMP + US + HUMIDITY + US + AIRPRESSURE  
  TIMESTAMP: "1234abcd" 32 Bit Hex-Value, Miilisecods since start
  US: ASCII Unit-Separator (= 31 = 0x1f)
  TEMP: 4 characters, example "23.5"  
  HUMIDITY: 2 characters, example "60"  
  AIRPRESSURE: 6 characters, example "1010.0"  

* on Request "measure": TIMESTAMP + US + ROLLENDREHZAHL + US + MOTORDREHZAHL
  TIMESTAMP: "1234abcd" 32 Bit Hex-Value, Miilisecods since connect
  ROLLENDREHZAHL: "1000.1" in rounds per minute
  MOTORDREHZAHL: "10000" in rounds per minute


#### Example

...


