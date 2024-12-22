# Number to Words

This program converts a numerical input into words and
passes these words as a string output parameter (e.g., `123.45` becomes "ONE HUNDRED TWENTY-THREE DOLLARS AND FORTY-FIVE CENTS").

---

## Usage Instructions

### To Run in Terminal
1. Open the terminal.
2. Navigate to the `src` directory:
   ```bash
   cd src
   javac -d out main/*.java
   java -cp out main.Main "<enter number>"
   
### Input Requirements
- Single numerical input
- Cannot be more than 10^10-1

### Exit codes
- 0: Success
- 1: Failure - Too many arguments given.
- 2: Failure - Invalid number input.


## Testing

I have added a TestConverter class to facilitate functionality testing for the Converter class.