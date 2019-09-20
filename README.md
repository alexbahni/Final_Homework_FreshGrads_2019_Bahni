# Final_Homework_FreshGrads_2019_Bahni
 
Proiectul contine 2 clase de test:
 - UsersRolesTests
 - DBInteractionsTests
 
 # FISIERUL DE CONFIG
 Contine informatii necesare pentru accesarea API-urilor:
 - BASE_URI
 - DEFAULT_USERNAME
 - DEFAULT_PASSWORD
 Si pentru conexiunea cu baza de date:
 - DATABASE_URL
 - DATABASE_USERNAME
 - DATABASE_PASSWORD
 Proprietatile din acest fisier sunt citite cu ajutorul clasei ReadPropertyFile
 
 # USERS ROLES TESTS
 # Scopul acestui test este de a verifica daca USERs isi respecta rolurile
 De exemplu:
 - Un user cu rolul de OWNER_ADMIN nu ar trebui sa aiba acces la endpointul API/VET
 - Un user cu rolul de VET_ADMIN nu ar trebui sa aiba acces la endpointul API/OWNERS
 
 A fost creata o metoda de test (checkAdminRights) care primeste ca si parametri 2 valori: 
 - Tipul user-ului ce urmeaza a fi creat (cu valorile: OWNER_ADMIN si VET_ADMIN)
 - Endpointul ce urmeaza a fi accesat (cu valorile: VET si OWNERS)
 
 Datele userului (username, parola, id) sunt setate automat folosind libraria Faker
 
 # DB INTERACTIONS TESTS
 # Scopul acestui test este de a verifica daca USERs se creaza in baza de date
 
