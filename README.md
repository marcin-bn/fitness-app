# Fitness App

---
Rest API for fitness and nutrition applications written in hexagonal architecture
## Features

---
### User
1. register
2. login
3. obtain JWT

### Activity
1. add activity
2. get a list of activities in the time frame
3. get last activities
4. calculate training stats based on GPS coordinates and heart rate
   - calories burned
   - average heart rate
   - average altitude
   - distance
   - average speed

### Body weight
1. add measurement
2. get a list of measurements in the time frame
3. calculate progress

### Calculators
1. BMI
2. BFI
3. caloric needs
4. calories burned

### Favorite foods
1. add favorite product
2. get user favourite products
3. get recommended products based on the algorithm for finding similar users

### Food
1. get product by:
    - id
    - barcode
    - name
2. get nutritional information about the product
   - for logged-in users:
     - calories, fat, proteins, carbohydrates, sugars, salt
   - for premium users:
     - all for logged-in users + vitamins and minerals

### Meal plan
1. add meal
2. update meal
3. delete meal
4. get meal plan for the specific day
5. calculate nutritional information for the meal plan

### Step counter
1. add measurement
2. calculate average steps
3. calculate average daily steps
4. get a list of measurements in the time frame

---
## Tech stack
- Java 17
- Spring 6
- Spring Boot 3
- PostgreSQL
- Docker
- Testcontainers

---
## External APIs used
- [Open Food Facts](https://openfoodfacts.github.io/api-documentation/)
- [Edamam](https://developer.edamam.com/food-database-api)
- [Open-Elevation](https://www.open-elevation.com/)