# Python Web Technologies

The "standard" is a library called **requests**. Works with https out of the box.

NOTE: check out https://httpbin.org/ for a utility site maintained by the same person who wrote the `requests` library.


## Making requests

```
import requests

resp = requests.get(url)
resp = requests.post(url, data={}) # send data as form encoded 'month=march'
resp = requests.put(url, data={})  # send data as form encoded
resp = requests.post(url, json={}) # will serialise dict to json in request body

```

`data` can take
* dictionary
* list of 2 element tuples
* bytes
* file like objects

### Checking success

Can inpect `resp.status_code`

The response object has a truthiness operator though so you can simply go

```
if resp:
```

### Checking error codes

```
   from requests.exceptions import HTTPError

   try:
        response = requests.get(url)
        response.raise_for_status()
    except HTTPError as err:
        print(f"{url} Failure with code {response.status_code}")
    except Exception as err:
        print(f"{url} Some non-HTTP error occurred")
    else:
        print(f"{url} Success")
```

### Response data

`response.content` contains a byte array of the response data.
`response.text` - string version of response
`reponse.json()` - dictionary
`response.headers` - dictionary - keys are case insensitive

### Query String parameters and headers

 
```
 response = requests.get(
                url,
                params={"q": "the query"}
                headers={"Accept":"application/json"
           )
```



## Auth

Basic AUTH: (default)


```
requests.get(url, auth=(username, password))
```

To be explicit about using Basic Auth:

```
from requests.auth import HTTPBasicAuth
requests.get(
   url=url
   auth=HTTPBasicAuth(username, password)
   )
```

Implementing own auth:

```
import requests
from requests.auth import AuthBase


class TokenAuth(AuthBase):
  def __init__(self, token):
    self.token = token
    
  def __call__(self, r):
      """Attach a custom token"""
      r.headers['X-Token'] = self.token
      return r

resp = requests.get(url, auth=TokenAuth('1234'))
```

      
## SSL Cert 

Implemented as part of the `certifi` package used by `requests`.

Cert verification happens automatically but if you want to disable it, add the `verify=False` flag to the request methods.


## Timeouts

default - wait indefinitely

Pass the `timeout=seconds` arg to the request methods (seconds can be a double)

Can also pass in a typle:

```
requests.get(url, timeout=(connection_timeout_in_seconds, response_timeout_in_seconds))
```

Handling timeouts

```
import requests
from requests.exceptions import Timeout

try:
  response = requests.get(url, timeout=1)
except Timeout:
  print("Timed out")
else:
  print("Success")
```

## Retries

By default, request will not be retried. Need to create a adaptor
to manage retrues:


```
import requests
from requests.adapters import HTTPAdapter
from requests.exceptions import ConnectionError


url = "https://httpbin.org/"
my_adapter = HTTPAdapter(max_retries=3)

session = requests.session()

session.mount(url, my_adapter)

try:
   session.get(url, timeout=(0.1, 2))
except ConnectionError as ce:
   print("Exceeded retries")

```




## The session object

* Allows TCP connection to be reused across multiple requests
* Persists certain objects

A `session` object has all the same methods as the Requests API



```
import requests

url = 'https://httpbin.org'

with requests.Session() as session:
    session.auth = ('username', 'password')
    response = session.get(url + '/headers')
    print(response.json())

```



   
   
   
