# LogicBoard
A CLI circuit designer written in java.
Will be archiving this repo soon as this was a passion project when I was a 
freshman in college. May implement this later with what I've learned since then.

Console Output of OneBitFullAdder circuit developed using the program:
<table>
<th>One bit full adder</th>
    <tr>
        <td>A</td>
        <td>B</td>
        <td>Cin</td>
        <td>|</td>
        <td>Cout</td>
        <td>Sum</td>
    </tr>
    <tr>
        <td>0</td>
        <td>0</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>0</td>
    </tr>
        <td>0</td>
        <td>0</td>
        <td>1</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>0</td>
        <td>1</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>0</td>
        <td>1</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>0</td>
        <td>0</td>
        <td>  </td>
        <td>0</td>
        <td>1</td>
    </tr>
        <td>1</td>
        <td>0</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>1</td>
        <td>0</td>
        <td>  </td>
        <td>1</td>
        <td>0</td>
    </tr>
        <td>1</td>
        <td>1</td>
        <td>1</td>
        <td>  </td>
        <td>1</td>
        <td>1</td>
    </tr>
</table>
Process finished with exit code 0

### Using the editor (boolean expression example)
In the CLI you will be presented the following prompt:
```text
Welcome to the LogicBoard gate simulator. This is a console
implementation of the application for testing purposes only.
Select a function of the application to use by entering the option's
corresponding number (to the left):
1. Browse Libraries
2. Create new LogicBoard
3. Help & Guides
4. Quit
>
```

To create a new gate with a boolean expression, type `2` and press `enter`.

You will then be prompted with the following
```text
Enter the names of the inputs for the new LogicBoard. Enter: @@ to exit:
> 
```
For each input you want, enter the name you want for each given input and press enter. 
For example, say I want a gate with 3 inputs, and I want them to be named `A`, `B`, and `C`.
Then you would do the following:
- type in `A` and press `enter`
- type in `B` and press `enter`
- type in `C` and press `enter`
- type in `@@` and press enter. This signifies you are done naming your inputs.

you will then be prompted to enter the name of your logicboard:
```text
Enter the name for the new LogicBoard:
>
```
Type in the name you want to name your new logic board. We'll name our example board `aNewTest`.

The CLI Circuit editor will be "opened" and you will get the main editor prompt:
```text
aNewTest Board has been created and save to the user library!
Enter the number that corresponds with the function you wish to use, enter 'b' or 'back' to exit editmode.
(Don't worry your board will automatically be saved!)
1. Add Gate
2. Add boolean expression
3. View board data
4. Remove Gate
5. Replace Gate
Back?
> 
``` 

Now, to build out your logic gate via a boolean expression, type in `2` and press `enter`.

You will then be prompted with the boolean expression editor:
```text
Available gate to use for expression (use their exact name in your expression): 
	- A
	- B
	- C
Enter the expression to add Example: ( A + B ) * ( C ' ) (Hint: no parentheses around root operator)
```
For now, we will use the example provided in the prompt.
<br>
**NOTE:** This parser is pretty basic, I wrote it in my freshman year of college so there isn't a lot of
error catching or space correcting code. So make sure there is a space between each input name and op character.
<br>
**NOTE:** The top level expression should NOT be wrapped in parentheses.

enter `( A + B ) * ( C ' )` and press enter.

You will then be taken back to the main editor prompt:
```Expression: ( A + B ) * ( C ' ) has been added to LogicBoard: aNewTest
   Enter the number that corresponds with the function you wish to use, enter 'b' or 'back' to exit editmode.
   (Don't worry your board will automatically be saved!)
   1. Add Gate
   2. Add boolean expression
   3. View board data
   4. Remove Gate
   5. Replace Gate
   Back?
   > ```
   let's check out the board data by typing `3` and pressing `enter`.
   We will be prompted with the logic gate info prompt:
   ```text
   What would you like to see in LogicBoard/Gate: aNewTest?
   1. TruthTable
   2. List of all Gates
   3. List of Gate Associations
   4. View Boolean expressions
   back?
   >
   ```
   You can check out a lot of cool info with your board but for now let's just checkout the list of
   gate associations by typing in `3` and pressing `enter`. Here is the output we get:
   ```text
Gate A: 
	outputTo: (AorB)

Gate B: 
	outputTo: (AorB)

Gate C: 
	outputTo: (Cnot)

Gate (AorB): 
	input1: A
	input2: B
	outputTo: ((AorB)and(Cnot))

Gate (Cnot): 
	input1: C
	outputTo: ((AorB)and(Cnot))

Gate ((AorB)and(Cnot)): 
	input1: (AorB)
	input2: (Cnot)
```

There are a ton of other things you can do, you can use the manual editor instead of using boolean expressions.
The prompts should do a decent job of guiding you through this. You can also add existing circuit designs/gates (either from the standard lib
or user lib, of which are the circuits you have made and saved). For each input of the gate/circuit design being added, it will list the current outputs from
your gates from which you choose, this is the "wiring up" process. You can also delete although I forget how correctly this feature was implemented so
sorry in advanced if it doesn't work, this is freshman level software design.

### Contributions
Not really looking for any contributions on this, was just a passion project from my freshman year in college that I want available to the community. 
Wasn't trying to re-invent the wheel, was just really digging my Digital Design class and wanted to use what skills I had to try and emulate a circuit 
designing tool.  
