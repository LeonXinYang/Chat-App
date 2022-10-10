# [Group 25] Report


## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Conflict Resolution Protocol](#conflict-resolution-protocol)
2. [Application Description](#application-description)
3. [Application UML](#application-uml)
3. [Application Design and Decisions](#application-design-and-decisions)
4. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
5. [Testing Summary](#testing-summary)
6. [Implemented Features](#implemented-features)
7. [Team Meetings](#team-meetings)

## Team Members and Roles

| UID | Name | Role |
| :--- | :----: | ---: |
| [u7166287] | [Qingxuan Yan] | [Developer, UI designer, data manager] |
| [u6646184] | [Youshang Wang] | [Developer, instrumental tester, artistic material designer] |
| [u7174459] | [Xin Yang] | [Developer, backend supporter, text parser researcher] |
| [u7233947] | [Zixin Feng] | [Developer, unit tester, data assistant] |

## Conflict Resolution Protocol

*1. If there is a conflict in our group, the first thing is to hold the meeting to solve the problem* 

*2. If it is failed, then we will hold the second meeting before asking tutor for help*

*3. If it is still failed, we will ask instructor for help*
## Application Description

*Our application is a next generation social app. It is based on the inspiration of the drift bottle in the real life.
We will write some secret into the bottle as the post and we want it private and only show it to some people.
However, sometimes we want to have a post which is public and want to share it with other people.
Therefore, we invent this app, with the unique feature that people can post and search the post in different modes,
private or public. We also build up two scenes for it. The square mode is to find or upload the public post 
while the seaside is to find the private post.


**Application Use Cases and or Examples**

This application targets for the young people who are under the work pressure or study pressure.
They often have bad feeling but they do not dare to share it on their traditional social network platform
which may cause the bad influence.
Then they come! They are my customers! They can change their modes as they want! When they want to share their secrets or complain someone,
then they could use the private drift bottle mode. When they have some great news, absolutely they could use the square to post their good news.

What they could do:
    1. follow the users they found it interesting.
    2. upload their post in public or private.
    3. find the public post in square or private post in the seaside.
    4. interact with the post and users. (like "Like", "Follow","Watch profile")

Target Users:
    1. Students.
    2. Young people under the high pressure jobs (like coding). 

## Application UML

Please See The Screenshot Folder

## Application Design and Decisions

*I used the following data structures in my project:*

1. *AVL Tree*

   * *Objective: It is used for storing the following users tree and following posts tree .*

   * *Locations: GlobalData.java line 17 and line 18*

   * *Reasons:*

     * *It is more efficient than Arraylist for searching for the following users' post.*

     * *For example, we have many users here, and we just want the following users post. AVL Tree could reduce the search time and improve efficiency*
    

**Grammars**

*Tag := < Hash > < String >*

*Hash := "#"*

*String := < String > | < Punctuation >< String > | < String >< Punctuation >*

*Punctuation := , | . | " | ; | ' | ( | ) | - |*

*Blank := " "*

The advantage of this grammar is that it will only care about how to find tags. I can store these tokens
with sequence and when the previous token as Hash and the current Token as String, then I know it is a tag.

**Tokenizer and Parsers**

*For tokenizer, we have two field to store the previous token and the current token, which is different*
from the code in the lab. We have 3 tokens here. TokenHash for "#"; TokenBlank for " "; and TokenString for all string like "abcd".
For the Parser, we determine that when we have TokenHash in the previous token and the TokenString in the current token, it will build up a tag. like "#COMP2100isCool".

**Design Pattern**

*Singleton* 

This design pattern is used in the post and user data's loading. When we start the program and we will load the local data to this class.
So we need to ensure there are only one instance in this class. It is unique.

*Facade*

The design for the facade is to improve the function efficiency and understanding, because the subsystem of user and post maybe 
very crowded and we want the UI designer could master the tool and method easily.
## Summary of Known Errors and Bugs
1. *Bug 1:*

- *When the post is updated after a "Like" operation, the number of "Like" has little possibility incorrect*
2. *Bug 2:*

- *After complete the search function, the app may be broken*

## Testing Summary

*[What features have you tested? What is your testing coverage?]*

**TestType: Espresso**

Test: Fragment1Test
*Number of test cases: 4*

Test: Fragment2Test
*Number of test cases: 4*

Test: LoginActivityTest
*Number of test cases: 4*

Test: ResgisterActivityTest
*Number of test cases: 3*

Test: SearchActivityTest
*Number of test cases: 4*


**TestType: Junit Test**
Test: AVLTreeTest
Class: AVLTree
*Number of test cases: 1*
*Code coverage: 71%*


Test: ParserTest
*Number of test cases: 2*
*Code coverage: 82% for Parser Class*
*Code coverage: 88% for Tokenizer Class*
*Code coverage: 63% for Token Class*

Test: PostTest
*Number of test cases: 2*
*Code coverage: 30% for Post Class*


*For all of these Screenshot, you can find it in the folder Screenshot*
## Implemented Features
###*UI Design and Testing:*

2.UI tests using espresso or similar. Please note that your tests must be of reasonable quality. (For UI testing, you may use something such as espresso) a. Espresso is not covered in lectures/labs, but is a simple framework to write Android UI tests. (hard)

### *User Interaction*

1.The ability to micro-interact with 'posts' (e.g. like, report, etc.) [stored in-memory]. (easy)

3.The ability for users to ‘follow’ other users. There must be an adjustment to either the user’s timeline in relation to their following users or a section specifically dedicated to posts by followed users. [stored in-memory] (medium)

###*User Privacy*

3.Privacy II: A user can only see a profile that is Public (consider that there are at least two types of profiles: public and private). (easy)

###*Firebase Integration*

1.Use Firebase to implement user Authentication/Authorisation. (easy)

###*Greater Data Usage*

2.User profile activity containing a media file (image, animation (e.g. gif), video). (easy)

4.User statistics. Provide users with the ability to see a report of total views, total followers, total posts, total likes, in a graphical manner. (medium)

## Team Meetings

- *[Team Meeting 1](./Meetings/Meeting1.md)*
- *[Team Meeting 2](./Meetings/Meeting2.md)*
- *[Team Meeting 3](./Meetings/Meeting3.md)*

