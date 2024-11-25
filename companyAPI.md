GET #obtain company list with response of id, name
GET /api/companies

GET #obtain a certain specific company with response of id, name
GET /api/companies/{companyId}

GET #obtain list of all employees under a certain specific company
GET /api/companies/{companyId}/employees

GET #Page query, page equals 1, size equals 5, it will return the data in company list from index 0 to index 4
GET /api/companies?page=1&size=5

PUT #update an employee with company
PUT /api/companies/{companyId}/employees/{employeeId}

POST #add a company
POST /api/companies

DELETE #delete a company
DELETE /api/companies/{companyId}