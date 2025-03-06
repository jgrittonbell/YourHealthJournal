# Your Health Journal

This is an independent project for Madison College's Enterprise Java Class.

### Problem Statement

Managing nutrition and blood glucose levels is a critical yet challenging task for individuals with
diabetes. Existing food tracking applications often lack integration with continuous glucose monitoring (CGM) systems,
making it difficult for users to correlate their food intake with blood sugar fluctuations.
Additionally, manually logging food and nutritional data can be tedious and error-prone.

This project aims to bridge that gap by developing a Java-based food journal application that seamlessly
integrates Nutritionix API for comprehensive nutritional insights and Dexcom CGMs through pydexcom API
for glucose monitoring. By enabling users to log meals through multiple methods—searching a database, scanning
barcodes, or entering custom foods—the app will provide a streamlined experience for tracking and analyzing
health data in one place.

### Project Technologies/Techniques
* Security/Authentication
    * AWS Cognito
* Database
    * MySQL 8.x
* ORM Framework
    * Hibernate Version TBD
* Dependency Management
    * Maven
* Web Services consumed using Java
    * Nutritionix - https://www.nutritionix.com/
    * pydexcom - https://github.com/gagebenne/pydexcom
* CSS
    * Bootstrap or Materialize
* Data Validation
    * Bootstrap Validator for front end
    * Explore Hibernate Validator?
    * Explore Data Display Frameworks
* Logging
    * Log4J2
* Hosting
    * AWS
* Tech I'd like to explore as part of this work
    * Amazon Rekognition
    * MongoDB
    * Hibernate Validator
    * Hibernate Search
* Project Lombok
* Unit Testing
    * JUnit tests to cover all testable logic
* IDE: IntelliJ IDEA

### Design

* [User Stories](DesignDocuments/userStories.md)
* [Screen Design](DesignDocuments/Screens.md)
* [Database Schema](DesignDocuments/DatabaseSchema.md)

---

### [Project Plan](DesignDocuments/ProjectPlan.md)

#### [TimeLog](TimeLog.md)
### ⚠️ Disclaimer

This software is **not a medical device** and should **not** be used for medical decisions or as a substitute for professional healthcare advice.

- Do **not** rely on this application for **real-time glucose monitoring, medical treatment decisions, or emergencies**.
- The app is provided **"as is"**, with no guarantees of accuracy or reliability.
- Always consult a **qualified healthcare professional** for any medical concerns.
- This software is **not associated with or endorsed by any equipment manufacturer**, and all trademarks are those of their respective owners.

For a full disclaimer, please see [DISCLAIMER.md](DISCLAIMER.md).