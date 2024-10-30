#   Make sure requests library is installed on your system, as well as Python 3.11, see README for instructions.
import requests

#   Sample query for next train to arrive at the East Falls Regional Rail station with Temple University as the final stop.
response = requests.get("https://www3.septa.org/api/NextToArrive/index.php?req1=East%20Falls&req2=Temple%20University&req3=1")

#   Output status code and returned values.
print("Status code: " + str(response.status_code))
print(response.json())

#   From here, data can be formatted neatly.
