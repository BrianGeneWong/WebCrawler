##GE WebCrawler Project

This is my web-crawler project for GE Digital.
I used the Gson library to parse all given test files since it can easily deserialize JSON files into java objects.

##Solution
I am assuming that "addresses" cannot repeat in the sample data.

In order to check if an address is invalid. I first need to get the set of all valid addresses. Then I can check the links, and add
them to the appropriate category as needed (success, invalid, skipped).


