### Frame Types

|  Type                          | Value  | Description |
|:-------------------------------|:-------|:------------|
| __RESERVED__                                     | 0x00 | __Reserved__ |
| [__SETUP__](#frame-setup)                         | 0x01 | __
Setup__: Sent by client to initiate protocol processing. |
| [__LEASE__](#frame-lease)                         | 0x02 | __
Lease__: Sent by Responder to grant the ability to send requests. |
| [__KEEPALIVE__](#frame-keepalive)                 | 0x03 | __Keepalive__: Connection keepalive. |
| [__REQUEST_RESPONSE__](#frame-request-response)   | 0x04 | __Request Response__: Request single response. |
| [__REQUEST_FNF__](#frame-fnf)                     | 0x05 | __Fire And Forget__: A single one-way message. |
| [__REQUEST_STREAM__](#frame-request-stream)       | 0x06 | __Request Stream__: Request a completable stream. |
| [__REQUEST_CHANNEL__](#frame-request-channel)     | 0x07 | __Request
Channel__: Request a completable stream in both directions. |
| [__REQUEST_N__](#frame-request-n)                 | 0x08 | __Request
N__: Request N more items with Reactive Streams semantics. |
| [__CANCEL__](#frame-cancel)                       | 0x09 | __Cancel Request__: Cancel outstanding request. |
| [__PAYLOAD__](#frame-payload)                     | 0x0A | __
Payload__: Payload on a stream. For example, response to a request, or message on a channel. |
| [__ERROR__](#frame-error)                         | 0x0B | __Error__: Error at connection or application level. |
| [__METADATA_PUSH__](#frame-metadata-push)         | 0x0C | __Metadata__: Asynchronous Metadata frame |
| [__RESUME__](#frame-resume)                       | 0x0D | __
Resume__: Replaces SETUP for Resuming Operation (optional) |
| [__RESUME_OK__](#frame-resume-ok)                 | 0x0E | __Resume
OK__ : Sent in response to a RESUME if resuming operation possible (optional) |
| [__EXT__](#frame-ext)                             | 0x3F | __Extension
Header__: Used To Extend more frame types as well as extensions. |

<a name="frame-setup"></a>

### SETUP Frame (0x01)

Setup frames MUST always use Stream ID 0 as they pertain to the connection.

The SETUP frame is sent by the client to inform the server of the parameters under which it desires to operate. The
usage and message sequence used is shown in [Connection Establishment](#connection-establishment).

One of the important parameters for a connection is the format, layout, and any schema of the data and metadata for
frames. This is, for lack of a better term, referred to here as "MIME Type". An implementation MAY use typical MIME type
values or MAY decide to use specific non-MIME type values to indicate format, layout, and any schema for data and
metadata. The protocol implementation MUST NOT interpret the MIME type itself. This is an application concern only.

Payload = Data + optional metadata + optional Data mimetype override + optional Metadata mimetype override

The encoding format for Data and Metadata are included separately in the SETUP.

extension flags on setup:

* F - if has one more extension after it
* I - if can be ignored
* A - if need ACK frame
* _
* 4 extension dependent

on ext:

* F - if has one more extension after it
* I - if can be ignored - is it needed?
* _
* _
* 4 extension dependent

on stream:

* F - if has one more extension after it
* I - if can be ignored - is it needed?
* _
* _
* 4 extension dependent

4 extension points:

* setup frame
* setup ok(ack) frame
* ext frame
* stream frame

TODO: may be move require respond to extension frame???

keep alive:
setup: provide interval + maxLifetime? ext: data + respond flag

resume:
setup:

* init: token
* resume: token + last position setup ok: if(resume) last position ext: current position stream: flag if stream is
  resumable - client + server agreement - configuratioble

lease:
setup: supported strategies setupOk: selected strategies stream: complexity? ext: permits (both client and server)

broker:
setup: route join ext: broker info | route add | route join stream: address

Frame Contents

### PAYLOAD Frame (0x0A)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-+-+---------+-------------------------------+
    |Frame Type |0|M|F|C|N|D|M|  Flags  |
    +---------------+-----------------------------------------------+
    |M| MIME ID/Len |   Data Encoding MIME Type                    ...
    +---------------+-----------------------------------------------+
    |M| MIME ID/Len |   Metadata Encoding MIME Type                ...
    +-------------------------------+-------------------------------+
                         Metadata & Data
```

//setup

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |X|M|A|    Flags    |
    +---------------+-----------------------------------------------+
    |M| MIME ID/Len |   Data Encoding MIME Type                    ...
    +---------------+-----------------------------------------------+
    |M| MIME ID/Len |   Metadata Encoding MIME Type                ...
    +---------------+-------------------------------+---------------+
    |         Major Version         |        Minor Version          |
    +---------------+---------------+-------------------------------+
                          Metadata & Setup Payload
                          
                          
     EXtension setup format = (8 + 8) = 2 bytes (frame = 4 + 2 = 6) min from 6 to 8
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |M| Ext ID/Len  |   Extension ID text                          ...
    +---------------+---------------+---------------+---------------+
    |F|I|A|_| Flags |        Extension Payload Length               | present if can I=true
    +-------------------------------+-------------------------------+
    |0|                 Time Between KEEPALIVE Frames               | here goes extension payload
    +---------------------------------------------------------------+
    |0|                       Max Lifetime                          |
    +---------------+-----------------------------------------------+
    
    //TODO:
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |M| MIME ID/Len |   Metadata Encoding MIME Type                ...
    +---------------+---------------+---------------+---------------+
    |              Metadata Length                  |
    +-----------------------------------------------+---------------+
    |                     Metadata Payload                         ...
    +---------------+-----------------------------------------------+
    |M| MIME ID/Len |   Metadata Encoding MIME Type                ...
    +---------------+-------------------------------+---------------+
    |              Metadata Length                  |
    +-----------------------------------------------+---------------+
    |                     Metadata Payload                         ...
    +---------------------------------------------------------------+
    
    +-------------------------------+-------------------------------+
    |0|0|0|0|           Number of Entries           |             
    +-------------------------------+---------------+---------------+
    |0|                         Stream ID                           |
    +-------------------------------+-------------------------------+
    |                                                               |
    |                       Implied Position                        |
    |                                                               |
    +-------------------------------+-------------------------------+
    |0|                         Stream ID                           |
    +-------------------------------+-------------------------------+
    |                                                               |
    |                       Implied Position                        |
    |                                                               |
    +-------------------------------+-------------------------------+

```

* __Frame Type__: (6 bits) 0x01
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
  * (__R__)esume Enable: Client requests resume capability if possible. Resume Identification Token present.
  * (__L__)ease: Will honor LEASE (or not).
* __Major Version__: (16 bits = max value 65,535) Unsigned 16-bit integer of Major version number of the protocol.
* __Minor Version__: (16 bits = max value 65,535) Unsigned 16-bit integer of Minor version number of the protocol.
* __Time Between KEEPALIVE Frames__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer of Time (in
  milliseconds) between KEEPALIVE frames that the client will send. Value MUST be > 0.
  * For server-to-server connections, a reasonable time interval between client KEEPALIVE frames is 500ms.
  * For mobile-to-server connections, the time interval between client KEEPALIVE frames is often > 30,000ms.
* __Max Lifetime__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer of Time (in milliseconds) that
  a client will allow a server to not respond to a KEEPALIVE before it is assumed to be dead. Value MUST be > 0.
* __Resume Identification Token Length__: (16 bits = max value 65,535) Unsigned 16-bit integer of Resume Identification
  Token Length in bytes. (Not present if R flag is not set)
* __Resume Identification Token__: Token used for client resume identification (Not present if R flag is not set)
* __MIME Length__: Encoding MIME Type Length in bytes.
* __Encoding MIME Type__: MIME Type for encoding of Data and Metadata. This SHOULD be a US-ASCII string that includes
  the [Internet media type](https://en.wikipedia.org/wiki/Internet_media_type) specified
  in [RFC 2045](https://tools.ietf.org/html/rfc2045). Many are registered with
  [IANA](https://www.iana.org/assignments/media-types/media-types.xhtml) such as
  [CBOR](https://www.iana.org/assignments/media-types/application/cbor).
  [Suffix](http://www.iana.org/assignments/media-type-structured-suffix/media-type-structured-suffix.xml)
  rules MAY be used for handling layout. For example, `application/x.netflix+cbor` or
  `application/x.reactivesocket+cbor` or `application/x.netflix+json`. The string MUST NOT be null terminated.
* __Setup Data__: includes payload describing connection capabilities of the endpoint sending the Setup header.

__NOTE__: A server that receives a SETUP frame that has (__R__)esume Enabled set, but does not support resuming
operation, MUST reject the SETUP with an ERROR[REJECTED_SETUP].

<a name="frame-error"></a>

### ERROR Frame (0x0B)

Error frames are used for errors on individual requests/streams as well as connection errors and in response to SETUP
frames.

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|0|      Flags    |
    +-----------+-+-+---------------+-------------------------------+
    |                          Error Code                           |
    +---------------------------------------------------------------+
                               Error Data
```

* __Frame Type__: (6 bits) 0x0B
* __Error Code__: (32 bits = max value 2^31-1 = 2,147,483,647) Type of Error.
  * See list of valid Error Codes below.
* __Error Data__: includes Payload describing error information. Error Data SHOULD be a UTF-8 encoded string. The string
  MUST NOT be null terminated.

A Stream ID of 0 means the error pertains to the connection., including connection establishment. A Stream ID > 0 means
the error pertains to a given stream.

The Error Data is typically an Exception message, but could include stringified stacktrace information if appropriate.

#### Error Codes

|  Type                          | Value      | Description |
|:-------------------------------|:-----------|:------------|
| __RESERVED__                   | 0x00000000 | __Reserved__ |
| __
INVALID_SETUP__              | 0x00000001 | The Setup frame is invalid for the server (it could be that the client is too recent for the old server). Stream ID MUST be 0. |
| __
UNSUPPORTED_SETUP__          | 0x00000002 | Some (or all) of the parameters specified by the client are unsupported by the server. Stream ID MUST be 0. |
| __
REJECTED_SETUP__             | 0x00000003 | The server rejected the setup, it can specify the reason in the payload. Stream ID MUST be 0. |
| __
REJECTED_RESUME__            | 0x00000004 | The server rejected the resume, it can specify the reason in the payload. Stream ID MUST be 0. |
| __
CONNECTION_ERROR__           | 0x00000101 | The connection is being terminated. Stream ID MUST be 0. Sender or Receiver of this frame MAY close the connection immediately without waiting for outstanding streams to terminate.|
| __
CONNECTION_CLOSE__           | 0x00000102 | The connection is being terminated. Stream ID MUST be 0. Sender or Receiver of this frame MUST wait for outstanding streams to terminate before closing the connection. New requests MAY not be accepted.|
| __APPLICATION_ERROR__          | 0x00000201 | Application layer logic generating a Reactive Streams _
onError_ event. Stream ID MUST be > 0. |
| __
REJECTED__                   | 0x00000202 | Despite being a valid request, the Responder decided to reject it. The Responder guarantees that it didn't process the request. The reason for the rejection is explained in the Error Data section. Stream ID MUST be > 0. |
| __
CANCELED__                   | 0x00000203 | The Responder canceled the request but may have started processing it (similar to REJECTED but doesn't guarantee lack of side-effects). Stream ID MUST be > 0. |
| __INVALID__                    | 0x00000204 | The request is invalid. Stream ID MUST be > 0. |
| __RESERVED__                   | 0xFFFFFFFF | __Reserved for Extension Use__ |

__NOTE__: Unsed values in the range of 0x0001 to 0x00300 are reserved for future protocol use. Values in the range of
0x00301 to 0xFFFFFFFE are reserved for application layer errors.

When this document refers to a specific Error Code as a frame, it uses this pattern: ERROR[error_code] or
ERROR[error_code|error_code]

For example:

- ERROR[INVALID_SETUP] means the ERROR frame with the INVALID_SETUP code
- ERROR[REJECTED] means the ERROR frame with the REJECTED code
- ERROR[CONNECTION_ERROR|REJECTED_RESUME] means the ERROR frame with either the CONNECTION_ERROR or REJECTED_RESUME code

<a name="frame-lease"></a>

### LEASE Frame (0x02)

Lease frames MAY be sent by the client-side or server-side Responders and inform the Requester that it may send Requests
for a period of time and how many it may send during that duration. See [Lease Semantics](#lease-semantics) for more
information.

The last received LEASE frame overrides all previous LEASE frame values.

Lease frames MUST always use Stream ID 0 as they pertain to the Connection.

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|M|     Flags     |
    +-----------+-+-+---------------+-------------------------------+
    |0|                       Time-To-Live                          |
    +---------------------------------------------------------------+
    |0|                     Number of Requests                      |
    +---------------------------------------------------------------+
                                Metadata
```

* __Frame Type__: (6 bits) 0x02
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
* __Time-To-Live (TTL)__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer of Time (in milliseconds)
  for validity of LEASE from time of reception. Value MUST be > 0.
* __Number of Requests__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer of Number of Requests
  that may be sent until next LEASE. Value MUST be > 0.

A Responder implementation MAY stop all further requests by sending a LEASE with a value of 0 for __Number of Requests__
or __Time-To-Live__.

When a LEASE expires due to time, the value of the __Number of Requests__ that a Requester may make is implicitly 0.

This frame only supports Metadata, so the Metadata Length header MUST NOT be included, even if the (M)etadata flag is
set true.

<a name="frame-keepalive"></a>

### KEEPALIVE Frame (0x03)

KEEPALIVE frames MUST always use Stream ID 0 as they pertain to the Connection.

KEEPALIVE frames MUST be initiated by the client and sent periodically with the (__R__)espond flag set.

KEEPALIVE frames MAY be initiated by the server and sent upon application request with the (__R__)espond flag set.

Reception of a KEEPALIVE frame with the (__R__)espond flag set MUST cause a client or server to send back a KEEPALIVE
with the (__R__)espond flag __NOT__ set. The data in the received KEEPALIVE MUST be echoed back in the generated
KEEPALIVE.

Reception of a KEEPALIVE by a server indicates to the server that the client is alive.

Reception of a KEEPALIVE by a client indicates to the client that the server is alive.

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+-+-------------+-------------------------------+
    |Frame Type |0|0|R|    Flags    |
    +-----------+-+-+-+-------------+-------------------------------+
    |0|                  Last Received Position                     |
    +                                                               +
    |                                                               |
    +---------------------------------------------------------------+
                                  Data
```

* __Frame Type__: (6 bits) 0x03
* __Flags__: (10 bits)
  * (__R__)espond with KEEPALIVE or not
* __Last Received Position__: (63 bits = max value 2^63-1) Unsigned 63-bit long of Resume Last Received Position. Value
  MUST be > 0. (optional. Set to all 0s when not supported.)
* __Data__: Data attached to a KEEPALIVE.

<a name="frame-request-response"></a>

### REQUEST_RESPONSE Frame (0x04)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-------------+-------------------------------+
    |Frame Type |X|M|F|     Flags   |
    +-------------------------------+
                         Metadata & Request Data
```

* __Frame Type__: (6 bits) 0x04
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
  * (__F__)ollows: More fragments follow this fragment.
* __Request Data__: identification of the service being requested along with parameters for the request.

<a name="frame-fnf"></a>

### REQUEST_FNF (Fire-n-Forget) Frame (0x05)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-------------+-------------------------------+
    |Frame Type |0|M|F|    Flags    |
    +-------------------------------+
                          Metadata & Request Data
```

* __Frame Type__: (6 bits) 0x05
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
  * (__F__)ollows: More fragments follow this fragment.
* __Request Data__: identification of the service being requested along with parameters for the request.

<a name="frame-request-stream"></a>

### REQUEST_STREAM Frame (0x06)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-------------+-------------------------------+
    |Frame Type |0|M|F|    Flags    |
    +-------------------------------+-------------------------------+
    |0|                    Initial Request N                        |
    +---------------------------------------------------------------+
                          Metadata & Request Data
```

* __Frame Type__: (6 bits) 0x06
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
  * (__F__)ollows: More fragments follow this fragment.
* __Initial Request N__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer representing the initial
  number of items to request. Value MUST be > 0.
* __Request Data__: identification of the service being requested along with parameters for the request.

See [Flow Control: Reactive Streams Semantics](#flow-control-reactive-streams) for more information on RequestN
behavior.

<a name="frame-request-channel"></a>

### REQUEST_CHANNEL Frame (0x07)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-+-----------+-------------------------------+
    |Frame Type |0|M|F|C|  Flags    |
    +-------------------------------+-------------------------------+
    |0|                    Initial Request N                        |
    +---------------------------------------------------------------+
                           Metadata & Request Data
```

* __Frame Type__: (6 bits) 0x07
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata present
  * (__F__)ollows: More fragments follow this fragment.
  * (__C__)omplete: bit to indicate stream completion.
    * If set, `onComplete()` or equivalent will be invoked on Subscriber/Observer.
* __Initial Request N__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer representing the initial
  request N value for channel. Value MUST be > 0.
* __Request Data__: identification of the service being requested along with parameters for the request.

A requester MUST send only __one__ REQUEST_CHANNEL frame. Subsequent messages from requester to responder MUST be sent
as PAYLOAD frames.

A requester MUST __not__ send PAYLOAD frames after the REQUEST_CHANNEL frame until the responder sends a REQUEST_N frame
granting credits for number of PAYLOADs able to be sent.

See Flow Control: Reactive Streams Semantics for more information on RequestN behavior.

<a name="frame-request-n"></a>

### REQUEST_N Frame (0x08)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|0|     Flags     |
    +-------------------------------+-------------------------------+
    |0|                         Request N                           |
    +---------------------------------------------------------------+
```

* __Frame Type__: (6 bits) 0x08
* __Request N__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer representing the number of items
  to request. Value MUST be > 0.

See Flow Control: Reactive Streams Semantics for more information on RequestN behavior.

<a name="frame-cancel"></a>

### CANCEL Frame (0x09)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|0|    Flags      |
    +-------------------------------+-------------------------------+
```

* __Frame Type__: (6 bits) 0x09

<a name="frame-payload"></a>

### PAYLOAD Frame (0x0A)

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+-+-+-+---------+-------------------------------+
    |Frame Type |0|M|F|C|N|  Flags  |
    +-------------------------------+-------------------------------+
                         Metadata & Data
```

* __Frame Type__: (6 bits) 0x0A
* __Flags__: (10 bits)
  * (__M__)etadata: Metadata Present.
  * (__F__)ollows: More fragments follow this fragment.
  * (__C__)omplete: bit to indicate stream completion.
    * If set, `onComplete()` or equivalent will be invoked on Subscriber/Observer.
  * (__N__)ext: bit to indicate Next (Payload Data and/or Metadata present).
    * If set, `onNext(Payload)` or equivalent will be invoked on Subscriber/Observer.
* __Payload Data__: payload for Reactive Streams onNext.

Valid combinations of (C)omplete and (N)ext flags are:

- Both (C)omplete and (N)ext set meaning PAYLOAD contains data and signals stream completion.
  - For example: An Observable stream receiving `onNext(payload)` followed by `onComplete()`.
- Just (C)omplete set meaning PAYLOAD contains no data and only signals stream completion.
  - For example: An Observable stream receiving `onComplete()`.
- Just (N)ext set meaning PAYLOAD contains data stream is NOT completed.
  - For example: An Observable stream receiving `onNext(payload)`.

A PAYLOAD MUST NOT have both (C)complete and (N)ext empty (false).

The reason for the (N)ext flag instead of just deriving from Data length being > 0 is that 0 length data can be
considered a valid PAYLOAD resulting in a delivery to the application layer with a PAYLOAD containing 0 bytes of data.

For example: An Observable stream receiving data via `onNext(payload)` where payload contains 0 bytes of data.

<a name="frame-metadata-push"></a>

### METADATA_PUSH Frame (0x0C)

A Metadata Push frame can be used to send asynchronous metadata notifications from a Requester or Responder to its peer.

METADATA_PUSH frames MUST always use Stream ID 0 as they pertain to the Connection.

Metadata tied to a particular stream uses the individual Payload frame Metadata flag.

Frame Contents

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|1|     Flags     |
    +-------------------------------+-------------------------------+
                                Metadata
```

* __Frame Type__: (6 bits) 0x0C

This frame only supports Metadata, so the Metadata Length header MUST NOT be included.

<a name="frame-ext"></a>

### EXT (Extension) Frame (0x3F)

The general format for an extension frame is given below.

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                           Stream ID                           |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |I|M|    Flags      |
    +-------------------------------+-------------------------------+
    |0|                      Extended Type                          |
    +---------------------------------------------------------------+
                          Depends on Extended Type...
```

* __Frame Type__: (6 bits) 0x3F
* __Flags__: (10 bits)
  * (__I__)gnore: Can the frame be ignored if not understood?
  * (__M__)etadata: Metadata Present.
* __Extended Type__: (31 bits = max value 2^31-1 = 2,147,483,647) Unsigned 31-bit integer of Extended type information.
  Value MUST be > 0.

<a name="frame-resume"></a>

### RESUME Frame (0x0D)

The general format for a Resume frame is given below.

RESUME frames MUST always use Stream ID 0 as they pertain to the connection.

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|0|    Flags      |
    +-------------------------------+-------------------------------+
    |        Major Version          |         Minor Version         |
    +-------------------------------+-------------------------------+
    |         Token Length          | Resume Identification Token  ...
    +---------------------------------------------------------------+
    |0|                                                             |
    +                 Last Received Server Position                 +
    |                                                               |
    +---------------------------------------------------------------+
    |0|                                                             |
    +                First Available Client Position                +
    |                                                               |
    +---------------------------------------------------------------+
```

* __Frame Type__: (6 bits) 0x0D
* __Major Version__: (16 bits = max value 65,535) Unsigned 16-bit integer of Major version number of the protocol.
* __Minor Version__: (16 bits = max value 65,535) Unsigned 16-bit integer of Minor version number of the protocol.
* __Resume Identification Token Length__: (16 bits = max value 65,535) Unsigned 16-bit integer of Resume Identification
  Token Length in bytes.
* __Resume Identification Token__: Token used for client resume identification. Same Resume Identification used in the
  initial SETUP by the client.
* __Last Received Server Position__: (63 bits = max value 2^63-1) Unsigned 63-bit long of the last implied position the
  client received from the server.
* __First Available Client Position__: (63 bits = max value 2^63-1) Unsigned 63-bit long of the earliest position that
  the client can rewind back to prior to resending frames.

<a name="frame-resume-ok"></a>

### RESUME_OK Frame (0x0E)

The general format for a Resume OK frame is given below.

RESUME OK frames MUST always use Stream ID 0 as they pertain to the connection.

```
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                         Stream ID = 0                         |
    +-----------+-+-+---------------+-------------------------------+
    |Frame Type |0|0|    Flags      |
    +-------------------------------+-------------------------------+
    |0|                                                             |
    +               Last Received Client Position                   +
    |                                                               |
    +---------------------------------------------------------------+
```

* __Frame Type__: (6 bits) 0x0E
* __Last Received Client Position__: (63 bits = max value 2^63-1) Unsigned 63-bit long of the last implied position the
  server received from the client.
