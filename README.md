*Database Schema:*

1. *Singer Collection:*
    - `_id` (Primary Key)
    - `name` (String, unique)

2. *Vote Collection:*
    - `_id` (Primary Key)
    - `userId` (Reference to User Collection)
    - `singerId` (Reference to Singer Collection)
    - `votedAt` (Date, stores the timestamp when the vote was cast)

3. *Competition Collection:*
   - `_id` (Primary Key)
   - `_startDateTime` (startDateTime)
   - `_endDateTime` (endDateTime )
   - `competitors` (List<Competitor>)
   - `voteCount` (Number, stores the total votes)
   
4. *Competitor Collection:*
    - `_id` (Primary Key)
    - `singerId` (String, unique)
    - `voteCount` (Number, stores the total votes received by the singer)

   

*Rationale for Choosing MongoDB:*

1. *Flexible Schema:* MongoDB's document-based storage allows for flexible and evolving data structures. This is suitable for accommodating changes in data requirements without major schema changes.

2. *Scalability:* MongoDB is designed for horizontal scalability. It can handle large amounts of data and high traffic loads. Sharding can be used to distribute data across multiple servers.

3. *Performance:* MongoDB's NoSQL design provides fast read and write operations, making it suitable for applications with high voting traffic.

4. *Document Storage:* Storing data in JSON-like BSON format allows for efficient storage and retrieval of complex data structures.

5. *Atomic Operations:* MongoDB supports atomic operations at the document level, ensuring consistency and data integrity.

6. *Replication and Fault Tolerance:* MongoDB offers replication features that ensure data redundancy and high availability. In case of a server failure, the system can automatically switch to a secondary server.

7. *Aggregation Framework:* MongoDB's aggregation framework enables efficient data aggregation and calculation of results, such as vote counts and winner determination.

8. *Ease of Development:* Spring Data MongoDB provides easy integration with Spring Boot, allowing seamless interaction between Java objects and MongoDB documents.



*Architecture Diagram:*

Here's a high-level architecture diagram illustrating the system components:


        +-----------------+
        |     Frontend    |
        | (Web/Mobile App)|
        +--------+--------+
                 |
    +------------+------------+
    |    Spring Boot Backend   |
    +------------+------------+
                 |
    +------------+------------+
    |         MongoDB Cluster   |
    +--------------------------+





## API Summary
This document summarizes the APIs that are available for the Voting App.



```/singers/add```
This endpoint is used to add a new singer.

HTTP Method: POST
Parameters:
name: The name of the singer.
Response:
201: The singer was added successfully.
400: The request was invalid.
401: The user is not authorized to add a singer.
403: The user does not have enough permissions to add a singer.



```/competitions/add```
This endpoint is used to add a new competition.

HTTP Method: POST
Parameters:
startDateTime: The start date and time of the competition.
endDateTime: The end date and time of the competition.
competitors: An array of objects that each represent a competitor in the competition. Each competitor object must have a singerId property that specifies the ID of the singer who is competing.
Response:
201: The competition was added successfully.
400: The request was invalid.
401: The user is not authorized to add a competition.
403: The user does not have enough permissions to add a competition.


```/votes/vote```
This endpoint is used to cast a vote for a singer in a competition.

HTTP Method: POST
Parameters:
userEmail: The email address of the user who is casting the vote.
singerId: The ID of the singer who is being voted for.
competitionId: The ID of the competition in which the vote is being cast.
timestamp: The timestamp of the vote.
Response:
201: The vote was cast successfully.
400: The request was invalid.
401: The user is not authorized to cast a vote.
403: The user does not have enough permissions to cast a vote.


```/competitions/result/{{competition_id}}```
This endpoint is used to get the result of a competition.

HTTP Method: GET
Parameters:
competition_id: The ID of the competition whose result is being requested.
Response:
200: The result of the competition was returned successfully.
400: The request was invalid.
401: The user is not authorized to get the result of a competition.
403: The user does not have enough permissions to get the result of a competition.
