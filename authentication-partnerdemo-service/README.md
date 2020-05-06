# ID-Authentication Partner Demo Serivce
This is a helper service module that can used for ID-Authentication functional testing. This exposes many helper services such as:
1. Creating Auth Request for creating request body of Partner Authentication, Internal Authentication and KYC Authenication request.
2. Encrypt/Decrypt data using Mosip Public which will internally call the Mosip-Kernel-CryptoManager service.
3. Encode/Decode data - to do base-64-url encoding/decoding
4. Encode/Decode from/to file - to do base-64-url encoding/decoding
5. JWS Sign/Verify

## Auth Request Creation Service 
This service can be used for creating requrest body of Partner Authentication, Internal Authentication and KYC Authenication request.

*POST /createAuthRequest?id=5879610697&idType=UIN&isInternal=false&isKyc=false*

For example:
http://localhost:8081/v1/identity/createAuthRequest?id=5879610697&idType=UIN&isInternal=false&isKyc=false

### Sample Request Body:
````
{
	"otp": "123456",
	"biometrics": [{
			"data": {
				"bioSubType": "UNKNOWN",
				"bioType": "FIR",
				"bioValue": "RklSADAyMAAAAC6AAAEBAQAALnAH4wsIETkBAeMAAAAAAAFZAEAADwIAQAIAQAIHAAEB9AH0AfQB9AgFHQEfAZ0AAC3",
				"deviceCode": "2a4af583-da31-4800-8cb9-9340643fd1bf",
				"deviceProviderID": "SYNCBYTE.LTC163",
				"deviceServiceID": "",
				"deviceServiceVersion": "SB.WIN.001",
				"digitalId": {
					"dateTime": "2019-12-05T10:41:15.052Z",
					"deviceProvider": "SYNCBYTE",
					"deviceProviderId": "SYNCBYTE.LTC163",
					"make": "Logitech",
					"model": "4d36e96c-e325-11ce-bfc1-08002be10316",
					"serialNo": "78467169",
					"subType": "Slab",
					"type": "Finger"
				},
				"env": "Staging",
				"mosipProcess": "Auth",
				"qualityScore": 70,
				"requestedScore": 70,
				"timestamp": "2019-12-05T10:41:15.070Z",
				"transactionId": "1234567890"
			}
		},
		{
			"data": {
				"bioSubType": "FACE",
				"bioType": "FACE",
				"bioValue": "RkFDADAzMAAAAFo7AAEAAAAAAFoqB+MKHQ4mDwAPAAAAAAABAAEDAQMAAAAAAAAAAAAAAAAAAAAAAAACAPABQAAAAQAAAABZ",
				"deviceCode": "b8c72d1b-2120-4d48-a72d-f75db4a2dd04",
				"deviceProviderID": "SYNCBYTE.LTC165",
				"deviceServiceID": "",
				"deviceServiceVersion": "SB.WIN.001",
				"digitalId": {
					"dateTime": "2019-12-05T10:44:07.526Z",
					"deviceProvider": "SYNCBYTE",
					"deviceProviderId": "SYNCBYTE.LTC165",
					"make": "Logitech",
					"model": "4d36e96c-e325-11ce-bfc1-08002be10318",
					"serialNo": "78467171",
					"subType": "Full face",
					"type": "Face"
				},
				"env": "Staging",
				"mosipProcess": "Auth",
				"qualityScore": 70,
				"requestedScore": 70,
				"timestamp": "2019-12-05T10:43:46.986Z",
				"transactionId": "1234567890"
			}
		}
	],
	"timestamp": "2019-04-04T09:41:57.086+05:30",
	"transactionID": "1234567890"
}
````

### Sample Response: Created Auth Request
````
{
  "consentObtained": true,
  "id": "mosip.identity.auth",
  "individualId": "5879610697",
  "individualIdType": "UIN",
  "keyIndex": "string",
  "request": "a35TsKHPgYAO5VjojI1ESZ7dQp616YnWs34nN05lLOwSNDPEhj",
  "requestHMAC": "RLtdlzViKi5siX2y52tMWkvDKPMZtrtyGLO2m9B7JkvnZx",
  "requestSessionKey": "nbLnLF0yLi4ggO3l0F54uE9FWwx6R9WRQtoTAzKn",
  "requestTime": "2019-12-05T12:46:47.085Z",
  "requestedAuth": {
    "bio": true,
    "demo": false,
    "otp": true,
    "pin": false
  },
  "transactionID": "1234567890",
  "version": "1.0"
}
````

### Reference
The complete reference of the ID-Authenication Partner Demo services can be viewed in below link (assuming it is launched in localhost:8081)

http://localhost:8081/v1/identity/v2/api-docs

## Device Registration Helper Services
Below are the various helper services that can be used for device registration.

* **/registerDeviceInfo** - This service is used to register device with the device information of a device got from the MDS service.
* **/registerDeviceInfos** - This service is used to register devices with the array of device information of multiple devices got from the MDS service.
* **/registerDecodedDeviceInfo** - This service is used to register device with the decoded device information of a device got from the MDS service.
* **/decodeDeviceInfo** - This service is used to decode the device information of a device got from the MDS service.
* **/decodeDeviceInfos** - This service is used to decode the array of device information of multiple devices got from the MDS service.
* **/validateDevice** - This service is used to validate the device information of a device got from the MDS service.
* **/validateDeviceInfoDecoded** - This service is used to validate the decoded device information of a device got from the MDS service.

**Please Note:** 
1. The application properties needs to be confgirued properly with below properties so that the the device registration should work.
* &lt;environment&gt;.db.url - for example `qa.db.url=jdbc:postgresql://127.0.0.1:9001/mosip_master`
* db.user - base64 encoded
* db.pass - base64 encoded

2. For registering device for 'Registration' purpose, it should be made sure to add the device to the master data database before invoking the device registration services.

3. This device registration service is compatible with Kernel MasterData device registration API of version greater than or equal to 1.0.6.
