# **Moneyfy - Exploratory Test Charter**

Exploratory test charter for the mobile application Moneyfy

```
Type                Priority        Estimate
Non-functional      High            30mins
```
**Goal:**
Verify the installation process

**Test Results:**

`Test Device - One Plus | 
RAM - 8 GB | 
OS - Android |`
- Installation completed in 30 secs without any issue
- New Icon available in applications list once installation is completed
- 'Open Application' button on playstore navigates to the Intro Page

Note: Should be validated across with different aspects like OS, memory capacity, Network speed 

-----------------------------------------------------------------------
```
Type                Priority        Estimate
Functional          High            30mins
```
**Goal**: 
Verify the home page

**Test Results:**

`Test Device - One Plus | RAM - 8 GB | OS - Android |
`

- Default currency is $
- Balance when we first open is $0
- Default categories available on the screen 
- Pie chart indicates correct amounts once we start adding data
- Along with pie chart correct values displayed in between the pie chart
- Balance gets updated as soon as we add any expense or income
- Buttons available navigate to correct pages - Expense, Income and balance
- Navigation bars - top right, top left
- Search option available at the top
- Transfer option available at the top
  
**Observations**

- When categories created are greater not all displayed on the home screen
- Future dates enabled to add income and expense

Note: mentioned above as observations as not sure if it is a feature created deliberately

--------------------------------------------------------------------------
```
Type                Priority        Estimate
Functional          High            30mins
```
**Goal**:
Verify the expense and income page

**Test Results:**

`Test Device - One Plus | RAM - 8 GB | OS - Android |
`
- Calendar option available to indicate date if expense
- Date is editable
- Accounts default Cash present and is editable to update to any available accounts
- Total allowed digits in amount field is 9
- 2 value accepted post decimal
- Only one '.' allowed in the amount field
- Calculator available at the bottom of the screen
- Addition, subtraction, multiplication and division operations performed on the value entered
- Notes field allows Alphabets,numbers and special characters
- Choose category option available at the bottom 
- If amount is 0 does not allow to choose category
- Back option available on the top
- Any value added in expense get deducted from total amount
- Any value added in income gets added in the total amount

**Observations**

- No text limit in the notes field

----------------------------------------------------------------------

```
Type                Priority        Estimate
Functional          High            30mins
```
**Goal**:
Verify the balance page

**Test Results:**

`Test Device - One Plus | RAM - 8 GB | OS - Android |
`
- Aggregated list of all expense for the selected time period
- Sorting option available - Can sort based on amount or day wise
- We can add expense or income from this page as well

----------------------------------------------------
```
Type                Priority        Estimate
Functional          Medium            30mins
```
**Goal**:
Right hand pannel available

**Test Results:**

`Test Device - One Plus | RAM - 8 GB | OS - Android |
`

We have 4 options available to update as per our requirements

**1. Categories**

- Allows to add new categories - Not available for default version gives a pop-up for upgrade
- Change icon for categories from available list
- Allows to delete existing category 
- On deleting existing category with records gives a pop-up message as a warning
- Enable or disable the category using checkbox

**2. Accounts**
- Allows to add new accounts - Not available for default version gives a pop-up for upgrade
- Change icon for categories from available list
- Allows to delete existing account
- On deleting existing account with records gives a pop-up message as a warning
- Enable or disable the account using checkbox
- We can setup initial balance in each account for the first time

**3. Currencies**

-Not available for default version gives a pop-up for upgrade

**4. Settings**
- Set up different account settings like language, currency to be used, carry over, etc.

----------------------------------------------------
```
Type                Priority        Estimate
Functional          Medium            30mins
```
**Goal**:
Left hand pannel available

**Test Results:**

`Test Device - One Plus | RAM - 8 GB | OS - Android |
`

**1. **Calendar**** 

adjust dates to add expense in past or future dates

**2. **Accounts****  
 
Choose account which should be utilized

**3. **Interval****  
 
Choose what data should be displayed if its monthly,daily,etc.

--------------------------------------------------
