# GovGrantDisbursement
RESTful API that would help decide on groups of people who are eligible for various upcoming government grants.

## Getting Started

### Requirements
Ensure the following are installed
* Java 17
* MongoDB
* Maven
* MongoDB Tools (Not required)

### Setting Up
* Clone the repository into local directory 
```
git clone https://github.com/ongzh/GovGrantDisbursement.git
```
### Executing the program
* start the mongodb server
```
mongod --port 27017
```
```
mvn spring-boot:run
```
### Database Set Up (Not required)
* Populate the database with sample data by locating the mongodump file provided
```
mongorestore --port 27017 <path>/mongodump/
```
### Executing unit tests
```
mvn clean test
```

## API EndPoints
#### FamilyMember 
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/familyMember/getAll`               | Retrieve all family members.             |
| `GET`    | `/api/familyMember/getById/id`                          | Retrieve family member with id #id.                       |
| `POST`   | `/api/familyMember/create`                  | Create a new family member.              |
| `DELETE`  | `/api/familyMember/delete/id`                          | Delete family member with id #id.                 |
| `PUT`   | `/api/familyMember/update/id`                 | Update family member with id #id.                 |
#### Household
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/household/getAll` | Retrieve all households. |
 |`GET`    | `/api/household/getById/id`                          | Retrieve household with id #id.                       |
 |`GET`    | `/api/household/getByMember/id`                          | Retrieve household that contains family member with id #id.                       |
 | `POST`   | `/api/household/create`                  | Create a new household.              |
| `DELETE` | `/api/household/delete/id`| Delete household with id #id.                  |
| `PUT`   | `/api/household/update/id`                 | Update household with id #id.                 |
| `PUT`   | `/api/household/addMember/id`                 | Add a family member to household with id #id.                 |
#### GrantDisbursement
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`   | `/api/GrantDisbursement/getStudentEncouragementBonusEligibility` | Retrieve list of households and their qualifying members that are eligible for Student Encouragement Bonus. |
|`GET`    | `/api/GrantDisbursement/getMultigenerationSchemeEligibility` | Retrieve list of households and their qualifying members that are eligible for Multigeneration Scheme. |
|`GET`    | `/api/GrantDisbursement/getBabySunshineGrantEligibility`                          | Retrieve list of households and their qualifying members that are eligible for Elder Bonus.       |
|`GET`    | `/api/GrantDisbursement/getElderBonusEligibility`                          | Retrieve list of households and their qualifying members that are eligible for Baby Sunshine Grant.       |
|`GET`    | `/api/GrantDisbursement/getYOLOGSTGrantEligibility`                          | Retrieve list of households and their qualifying members that are eligible for YOLO GST Grant.     |

### Assumptions
* Each family member only belong to a single household
* People are spouses with each other. i.e FamilyMemberA with spouseB will result in FamilyMemberB with spouseA
## License
This project is available for use under the MIT License.



