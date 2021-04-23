# RestAssuredDemo

Sample Rest Assured Project to automate omdapi site:

Prerequisites to run the test:

Make sure java, Maven and TestNG is configured in the System.

STEPS TO RUN:

Goto https://www.omdbapi.com/ and sign up for an API Key. You'll need this API key to run the test.
Build a client that contains following methods using the OMDb API:
search(string): returns a list of all results that matched that search string. Takes pagination in account when generating the list. Query uses s parameter as specified here.
get_by_id(string): returns the result based on the input id e.g. tt999999. Query uses i parameter as specified here.
get_by_title(string): returns the result based on input string as title name. Query uses t parameter as specified here.
Write a test that does the following:
Using search method, search for all items that match the search string stem
Assert that the result should contain at least 30 items
Assert that the result contains items titled The STEM Journals and Activision: STEM - in the Videogame Industry
From the list returned by search above, get imdbID for item titled Activision: STEM - in the Videogame Industry and use it to get details on this movie using get_by_id method.
Assert that the movie was released on 23 Nov 2010 and was directed by Mike Feurstein
Using get_by_title method, get item by title The STEM Journals and assert that the plot contains the string Science, Technology, Engineering and Math and has a runtime of 22 minutes.
