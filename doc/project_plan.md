# Project Plan

#### Terminology Explanation

backend management system: **admin side platform**, where admin can create pages to manage product, member, inventory, order, coupon.

mall system: **customer side platform**, which performs the normal online shopping logic.

## Week1

#### Design Database for backend management system(done)
Design a database name worldbuy_admin, which is used by the backend management system.

## Week2

#### Develop the first version of the backend management system (done)

Technology selection: Vue as the front end tool, Springboot and Mybatis as backend tool.

In the first version of the backend management system, admin can create menu to manage the mall system

## Week3

#### Design Database for mall system(done)
Design 5 database:
- worldbuy_pms for product microservice module
- worldbuy_ums for user/member microservice module
- worldbuy_oms for order microservice module
- worldbuy_wms for inventory microservice module
- worldbuy_sms for coupon microservice module

## Week4

#### 1. Integrate Nacos as a microservice registration, discovery, and configuration center(done)

#### 2. Further development of the background management system for product management module(done)

- Develop product category management function(add, delete, edit, search 3 levelcategory).
- Develop product attribute management function(add, delete, edit, search category attribute).
- Develop product brand management function(combine attributes to brand).

## Week5
#### 1. Further development of the backend management system for member and inventory management modules(done)
- Develop member search function.
- Develop member level management function(level up/level down).
- Develop inventory management function(search, delete, edit).

#### 2. Integrate elastic search and test for product searching(done)

## Week 6

#### Develop the frontpage for mall system

## Week 7

#### 1. Integrate Redis to increse the performance of 3 level category searching task

#### 2. Integrate Redisson as the distributed lock

## Week 8

#### 1. Ingetrate thread Pool

#### 2. Develop the single sign-on function

## Week 9

#### 1. Develop product information page for the mall system

#### 2. Develop shopping cart function

## Week 10

#### Develop the order service

Add product to cart, remove product from cart, place an order, pay

#### Ingetrate Seata to solve Distributed Transaction Problems

## Week 11

#### Use Jmeter for pressure test and JVisualVM for jvm tuning

## Week 12

#### Deploy the whole system on cloud server with docker
