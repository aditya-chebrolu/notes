# **DynamoDB Python Interaction Cheatsheet**

## **Index**
1. [Setup](#setup)
2. [Table Management](#table-management)
   - [Create a Table](#create-a-table)
   - [List All Tables](#list-all-tables)
   - [Describe a Table](#describe-a-table)
   - [Delete a Table](#delete-a-table)
3. [Insert Operations](#insert-operations)
   - [Insert a Single Item](#insert-a-single-item)
   - [Batch Insert Items](#batch-insert-items)
4. [Retrieve Operations](#retrieve-operations)
   - [Get a Single Item](#get-a-single-item)
   - [Query Items (By Partition Key)](#query-items-by-partition-key)
   - [Scan (Retrieve All Items)](#scan-retrieve-all-items)
   - [Scan with Filtering](#scan-with-filtering)
   - [Paginated Scan](#paginated-scan)
5. [Update Operations](#update-operations)
   - [Update an Item](#update-an-item)
   - [Increment/Decrement an Attribute](#incrementdecrement-an-attribute)
   - [Conditional Update](#conditional-update)
6. [Delete Operations](#delete-operations)
   - [Delete an Item](#delete-an-item)
   - [Batch Delete Items](#batch-delete-items)
   - [Conditional Delete](#conditional-delete)
7. [Indexes](#indexes)
   - [Query Using Global Secondary Index (GSI)](#query-using-global-secondary-index-gsi)
   - [Query Using Local Secondary Index (LSI)](#query-using-local-secondary-index-lsi)
8. [Batch Operations](#batch-operations)
   - [Batch Write (Put/Delete Multiple Items)](#batch-write-putdelete-multiple-items)
   - [Batch Get](#batch-get)
9. [Transaction Operations](#transaction-operations)
   - [Transaction Write (ACID)](#transaction-write-acid)
   - [Transaction Read](#transaction-read)
10. [Time-to-Live (TTL)](#time-to-live-ttl)
    - [Enable/Update TTL](#enableupdate-ttl)
11. [Streams](#streams)
    - [List Streams](#list-streams)
    - [Get Stream Records](#get-stream-records)
12. [Miscellaneous](#miscellaneous)
    - [Export Table to S3](#export-table-to-s3)
13. [Best Practices](#best-practices)

---

## **Setup**
1. Install `boto3`:
   ```bash
   pip install boto3
   ```
2. Initialize DynamoDB resource and client:
   ```python
   import boto3
   dynamodb = boto3.resource('dynamodb')
   client = boto3.client('dynamodb')
   ```

---

## **Table Management**

### Create a Table
```python
table = dynamodb.create_table(
    TableName='Users',
    KeySchema=[{'AttributeName': 'userId', 'KeyType': 'HASH'}],
    AttributeDefinitions=[{'AttributeName': 'userId', 'AttributeType': 'S'}],
    ProvisionedThroughput={'ReadCapacityUnits': 5, 'WriteCapacityUnits': 5}
)
print("Table status:", table.table_status)
```

### List All Tables
```python
tables = client.list_tables()
print("Tables:", tables['TableNames'])
```

### Describe a Table
```python
response = client.describe_table(TableName='Users')
print(response['Table'])
```

### Delete a Table
```python
table = dynamodb.Table('Users')
table.delete()
print("Table deleted.")
```

---

## **Insert Operations**

### Insert a Single Item
```python
table = dynamodb.Table('Users')
table.put_item(
    Item={
        'userId': '123',
        'name': 'John Doe',
        'age': 30,
        'email': 'john.doe@example.com'
    }
)
```

### Batch Insert Items
```python
with table.batch_writer() as batch:
    batch.put_item(Item={'userId': '1', 'name': 'Alice'})
    batch.put_item(Item={'userId': '2', 'name': 'Bob'})
```

---

## **Retrieve Operations**

### Get a Single Item
```python
response = table.get_item(Key={'userId': '123'})
item = response.get('Item')
print("Item:", item)
```

### Query Items (By Partition Key)
```python
response = table.query(
    KeyConditionExpression=boto3.dynamodb.conditions.Key('userId').eq('123')
)
print("Query result:", response['Items'])
```

### Scan (Retrieve All Items)
```python
response = table.scan()
items = response.get('Items', [])
print("All items:", items)
```

### Scan with Filtering
```python
from boto3.dynamodb.conditions import Attr

response = table.scan(
    FilterExpression=Attr('age').gte(25)
)
print("Filtered items:", response['Items'])
```

### Paginated Scan
```python
items = []
response = table.scan()
items.extend(response['Items'])
while 'LastEvaluatedKey' in response:
    response = table.scan(ExclusiveStartKey=response['LastEvaluatedKey'])
    items.extend(response['Items'])
print("All paginated items:", items)
```

---

## **Update Operations**

### Update an Item
```python
table.update_item(
    Key={'userId': '123'},
    UpdateExpression="SET #attr = :val",
    ExpressionAttributeNames={"#attr": "email"},
    ExpressionAttributeValues={":val": "new.email@example.com"}
)
```

### Increment/Decrement an Attribute
```python
table.update_item(
    Key={'userId': '123'},
    UpdateExpression="SET #attr = #attr + :increment",
    ExpressionAttributeNames={"#attr": "age"},
    ExpressionAttributeValues={":increment": 1}
)
```

### Conditional Update
```python
table.update_item(
    Key={'userId': '123'},
    UpdateExpression="SET #attr = :val",
    ConditionExpression="attribute_exists(userId)",
    ExpressionAttributeNames={"#attr": "email"},
    ExpressionAttributeValues={":val": "conditional.email@example.com"}
)
```

---

## **Delete Operations**

### Delete an Item
```python
table.delete_item(Key={'userId': '123'})
```

### Batch Delete Items
```python
with table.batch_writer() as batch:
    batch.delete_item(Key={'userId': '1'})
    batch.delete_item(Key={'userId': '2'})
```

### Conditional Delete
```python
table.delete_item(
    Key={'userId': '123'},
    ConditionExpression="attribute_exists(userId)"
)
```

---

## **Indexes**

### Query Using Global Secondary Index (GSI)
```python
response = table.query(
    IndexName='GSI_Index_Name',
    KeyConditionExpression=boto3.dynamodb.conditions.Key('GSI_Key').eq('value')
)
print("Query result:", response['Items'])
```

### Query Using Local Secondary Index (LSI)
```python
response = table.query(
    IndexName='LSI_Index_Name',
    KeyConditionExpression=boto3.dynamodb.conditions.Key('userId').eq('123') &
                            boto3.dynamodb.conditions.Key('SortKey').eq('value')
)
print("Query result:", response['Items'])
```

---

## **Batch Operations**

### Batch Write (Put/Delete Multiple Items)
```python
client.batch_write_item(
    RequestItems={
        'Users': [
            {'PutRequest': {'Item': {'userId': '3', 'name': 'Charlie'}}},
            {'DeleteRequest': {'Key': {'userId': '2'}}}
        ]
    }
)
```

### Batch Get
```python
response = client.batch_get_item(
    RequestItems={
        'Users': {
            'Keys': [{'userId': '1'}, {'userId': '3'}]
        }
    }
)
print("Batch items:", response['Responses']['Users'])
```

---

## **Transaction Operations**

### Transaction Write (ACID)
```python
client.transact_write_items(
    TransactItems=[
        {
            'Put': {
                'TableName': 'Users',
                'Item': {'userId': '4', 'name': 'David'}
            }
        },
        {
            'Update': {
                'TableName': 'Users',
                'Key': {'userId': '3'},
                'UpdateExpression': "SET #name = :new_name",
                'ExpressionAttributeNames': {'#name': 'name'},
                'ExpressionAttributeValues': {':new_name': 'Updated Name'}
            }
        }
    ]
)
```

### Transaction Read
```python
response = client.transact_get_items(
    TransactItems=[
        {'Get': {'TableName': 'Users', 'Key': {'userId': '1'}}},
        {'Get': {'TableName': 'Users', 'Key': {'userId': '3'}}}
    ]
)
print("Transaction items:", response['Responses'])
```

---

## **Time-to-Live (TTL)**

### Enable/Update TTL
```python
client.update_time_to_live(
    TableName='Users',
    TimeToLiveSpecification={
        'Enabled': True,
        'AttributeName': 'ttl'
    }
)
```

---

## **Streams**

### List Streams
```python
response = client.list_streams(TableName='Users')
print("Streams:", response['Streams'])
```

### Get Stream Records
```python
stream_arn = "your_stream_arn"
response = client.get_records(
    ShardIterator="your_shard_iterator"
)
print("Stream Records:", response['Records'])
```

---

## **Miscellaneous**

### Export Table to S3
```python
client.export_table_to_point_in_time(
    TableArn='your_table_arn',
    S3Bucket='your_bucket_name',
    ExportFormat='DYNAMODB_JSON'
)
```

---

## **Best Practices**
1. Use **indexes** for efficient querying.
2. Leverage **batch operations** for bulk reads/writes.
3. Always handle **pagination** for large datasets.
4. Use **ConditionExpression** to enforce integrity during updates/deletes.
5. Monitor costs using **ConsumedCapacity** in responses.


