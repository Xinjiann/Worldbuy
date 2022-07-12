# User Story

- [**Backend Management System (Administrator Side)**](#backend-management-system)
   - [Administrator System Management](#administrator-system-management)
   - [Member System Management](#member-system-management)
   - [Product System Management](#product-system-management)
   - [Discount System Management](#discount-system-management)
   - [Iventory System Management](#iventory-system-management)
   - [Order System Management](#order-system-management)
 - [**Mall System (User Side)**](#mall-system)

## Backend Management System

### Administrator System Management

1. **As an administrator, I want to manage administrator role, including add, remove and level up other administrator.**
   
   Description: There are 2 different role 1 and 2, level 1 can add, delete and level up other administrator to level 2, level 1 administrator can only add administrator.

2. **As an administrator, I want to search other administrator by name.**
3. **As an administrator, I want to check operation logs by all administrators**
   
   Description: Every operations by administrator will be recorded and shown.
   
### Member System Management

4. **As an administrator, I want to check the member list**
5. **As an administrator, I want to manage member level**
6. **As an administrator, I want to manage member's bonus points**
7. **As an administrator, I want to check the member statistics**
   
   Description: member statistics includes drawing member level graph, member purchase amount graph.etc
### Product System Management
8. **As an administrator, I want to manage product category**

   Descriotion: Administrator can add, delete and edit product categories.The categories are in three level architecture, for example:
```
- Books, audio and video, e-books
   - Technology  
     - Computers and Internet  
     - Medical
   - Art
     - Novel
```
9. **As an administrator, I want to manage brand**
   
   Description: Administrator can add, remove and edit brand, including uploading images, changing visibility, setting search letter, and associating the brand with the specified category

10. **As an administrator, I want to manage product attributes**

   Description: each category has several attributes group, and each attribue group contains several attributes that can descrip current category. For example: Category phone has 2 attribute group, info, chip, where attribute group chip contains attribute like CPU brand, CPU model.
   
11. **As an administrator, I want to release product**

   Description: Administrator can release product, there will be 4 step: Set basic info like product name, description, price, image, set attribute, set SKU info, final step is to confirm and save information. **The released product will be in not exhibited status and waiting for goods on.**
   
12. **As an administrator, I want to set a product to be on sale**

13. **As an administrator, I want to manage the onsale product**
   Description: Administrator can manage the onsale product including combine coupons with the current prouct, check comments, goods off.etc
 
### Discount System Management
14. **As an administrator, I want to create coupons**
15. **As an administrator, I want to combine coupons with product**
16. **As an administrator, I want to check the coupons distribution logs**
17. **As an administrator, I want to set the bouns point for product**

### Iventory System Management
18. **As an administrator, I want to check iventory balance**
19. **As an administrator, I want to create purchasing List**
20. **As an administrator, I want to consolidated Purchase List**

### Order System Management
21. **As an administrator, I want to check orders**
22. **As an administrator, I want to review refund requests**
23. **As an administrator, I want to check the order statistics**

    Description: order statistics includes drawing order quantity graph, order amount graph.etc

## Mall System

24. **As a customer, I want to creat an account**
25. **As a customer, I want to login/log out**
26. **As a customer, I want to edit my profile**
27. **As a customer, I want to change my password**
28. **As a customer, I want to search product**
29. **As a customer, I want to check product information**
30. **As a customer, I want to add product to cart**
31. **As a customer, I want to place an order and pay**
32. **As a customer, I want to check my orders**
33. **As a customer, I want to refund product**
34. **As a customer, I want to comment product**



   
