# YourHealthJournal - Updated Project Plan

## Overview

This updated project plan reflects the actual development path taken during the YourHealthJournal project. Several tasks deviated from the original plan due to iterative design, implementation needs, and integration with AWS services.
### Weeks 1–4

- [x] Set up GitHub repository and initial project structure ✅ 2025-05-11

- [x] Wrote problem statement and user stories ✅ 2025-05-11

- [x] Identified MVP features and began refining them over time ✅ 2025-05-11

- [x] Designed initial UI using diagrams and Moqups ✅ 2025-05-11

- [x] Researched Nutritionix and Dexcom/Nightscout APIs ✅ 2025-05-11

- [x] Submitted Checkpoint 1 (2/12 - Week 4) ✅ 2025-05-11

### Week 5 – Hibernate Setup and Database Design

- [x] Finalized database schema (iterated on it later with User ID changes) ✅ 2025-05-11

- [x] Set up local MySQL database ✅ 2025-05-11

- [x] Created Hibernate `hibernate.cfg.xml` with secrets manager support ✅ 2025-05-11

- [x] Created and tested DAOs for key entities (Food, Meal, User) ✅ 2025-05-11

- [x] Began refactoring for long-term maintainability ✅ 2025-05-11

### Week 6 – DAO CRUD and Logging (and more schema changes)

- [x] Completed DAO CRUD operations and unit tests using JUnit ✅ 2025-05-11

- [x] Set up and configured Log4j2 ✅ 2025-05-11

- [x] Migrated primary key on User from Cognito ID to `Long id`, introduced unique Cognito ID field ✅ 2025-05-11
### Week 7 – Cognito Authentication and Filters

- [x] Integrated AWS Cognito for user login and signup (hosted UI) ✅ 2025-05-11

- [x] Created User entity and DAO with refactored relationships ✅ 2025-05-11

- [x] Wrote servlet filters (AuthFilter and JWTFilter) ✅ 2025-05-11

- [x] Tested login and session handling locally and with hosted UI ✅ 2025-05-11

### Week 8 – API Integration and Registration

- [x] Implemented Nutritionix natural language search API via REST endpoint ✅ 2025-05-11

- [x] Began Angular setup (separate repo) ✅ 2025-05-11

- [x] Created RegisterUser servlet to populate DB after Cognito signup ✅ 2025-05-11

- [x] Did not implement Dexcom API; manual glucose logging only ✅ 2025-05-11

### Week 9 – REST API and Angular Frontend

- [x] Created RESTful APIs for Food, Meal, Glucose endpoints ✅ 2025-05-11

- [x] Began Angular app with standalone components for FoodJournal and GlucoseJournal ✅ 2025-05-11

- [x] Used Bootstrap and Angular Material for UI components ✅ 2025-05-11

### Week 10–11 – Team Project

- [x] Worked on separate team project (Bug-Busters) ✅ 2025-05-11

- [x] Continued minor progress on YourHealthJournal (code cleanup, planning) ✅ 2025-05-11

### Week 12 – Presentations and Feedback

- [x] Completed team presentations ✅ 2025-05-11

- [x] Cleaned up backend controller and service code ✅ 2025-05-11

- [x] Implemented UI toggling and form refresh functionality ✅ 2025-05-11


### Weeks 13–14 – Polish and UI Integration

- [x] Refined Angular frontend to use services for API calls ✅ 2025-05-11

- [x] Linked user-specific data to journal entries (via JWT session) ✅ 2025-05-11

- [x] Replaced JSP dashboard with Angular dashboard ✅ 2025-05-11

- [x] Integrated form-based login/logout state across app ✅ 2025-05-11

### Weeks 15–16 – Final Testing and Submission

- [ ] Final bug fixes, styling, and UI toggling improvements

- [ ] Fully working MVP with: FoodJournal, GlucoseJournal(Manual Only) and Basic Data Insights

- [x] Did NOT deploy to AWS; decision made to keep local for final submission ✅ 2025-05-11

- [ ] Nutritionix API

- [ ] Angular Frontend with Bootstrap

- [ ] Final Submission (5/14)