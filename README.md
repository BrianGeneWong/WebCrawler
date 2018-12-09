#GE WebCrawler Project

This is my web-crawler project for GE Digital.
I used the Gson library to parse all given test files since it can easily deserialize JSON files into java objects.

#Solution
I am assuming that "addresses" cannot repeat in the sample data.

In order to check if an address is invalid. I first need to get the set of all valid addresses. Then I can check the links against that set
, and add them to the appropriate category as needed (success, invalid, skipped). I converted the JSON into a hashmap to more easily
obtain addresses and their corresponding links.

In crawl(), I use a threadpool to process the queue of pages more quickly. I used the same process in main to process both test files
for simplicities sake.
