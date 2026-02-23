# Experiment 5 - Calculator 2.0
This folder contains the files and resources for Experiment 5 (Advanced Version) of the Mobile Application Development course.

## Contents
This experiment focuses on building an advanced Calculator application. The app allows users to:

- Input numbers using on-screen buttons (0-9)
- Perform basic arithmetic operations (Addition, Subtraction, Multiplication, Division)
- Calculate exponentiation (Power operations)
- Use infix to postfix conversion for expression evaluation
- Clear last input or reset the entire calculation
- View real-time expression display
- Handle complex expressions with proper operator precedence

## Features
- **Number Buttons**: Dedicated buttons for digits 0-9
- **Basic Operations**: Add (+), subtract (-), multiply (*), and divide (/) numbers
- **Power Operation**: Calculate exponentiation using the ^ operator
- **Smart Input Validation**: Prevents consecutive operator entry
- **Expression Parsing**: Converts infix notation to postfix for accurate evaluation
- **Operator Precedence**: Properly handles order of operations (BODMAS/PEMDAS)
- **Clear Function**: Remove last character with Clear button
- **Reset Function**: Clear entire expression with Reset button
- **Dual Display**: Shows current expression and error messages separately
- **Error Handling**: Prevents division by zero and invalid inputs
- **Real-time Calculation**: Instant results on pressing the equals (Ans) button
- **Clean UI**: Intuitive calculator interface built with Kotlin

## Set Up
1. Download the folder
2. Open it in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## Technical Implementation
- **Algorithm**: Shunting Yard Algorithm for infix to postfix conversion
- **Evaluation**: Stack-based postfix expression evaluation
- **Operator Precedence**: ^ (3) > *, / (2) > +, - (1)
- **Error Validation**: Input validation and arithmetic error handling

## UI

<img width="358" height="643" alt="Screenshot 2026-02-11 222606" src="https://github.com/user-attachments/assets/c5cdf805-3717-4dbc-b41b-6a9fa0f9feb2" />


## App Demo


https://github.com/user-attachments/assets/7d61e64a-8a1e-4238-8a5e-689662f4e1ad





