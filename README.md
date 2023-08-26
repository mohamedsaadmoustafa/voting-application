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
