# Best Notes
BestNotes is a Python recreation of the app/program GoodNotes, a note-taking application. 
The user gets to create a digital notebook 
where they can write down notes for class or personal uses like journaling.
Through either drawing by their mouse or a stylus, users can create drawings as well as hand-written notes 
that they can easily move around by using a select tool. The program will also have a text feature, 
that allows the user to type their notes, which is easily moveable through the select tool. 
The notebooks have an autosave feature that saves these notebooks to the user’s devices. 
This program also has a collaboration feature that allows users to work together on a notebook. 

![image](https://github.com/user-attachments/assets/32b973fa-7ff2-471e-81f0-33bcd8a35adf)

# How to run
- Download and run the main.exe file from the latest [release](https://github.com/cis3296f24/01-BestNotes/releases).

# How to build
[BestNotes Project Board](https://github.com/orgs/cis3296f24/projects/94/)

### Windows instructions
#### PyCharm instructions
PDF Instructions for PyCharm: [BestNotes PyCharm Run Instructions.pdf](https://github.com/user-attachments/files/17853496/BestNotes.PyCharm.Run.Instructions.pdf)

- You need to download Python. You can do this from the Command Prompt by just typing ‘python’. The Microsoft store will open to the software page and you can click ‘Get’ to start a download. **If you downloaded Python through python.org before, please download it again through the Windows store anyway.**
- Next, download and install PyCharm
- Then download and install VLC media player: https://www.videolan.org/vlc/
- Clone the repository link into PyCharm
- Once in PyCharm, when you try to run main.py, you will get an error message stating that there is no Python Interpreter configured for the project. Click “Configure Python Interpreter > Add New Interpreter >Add Local Interpreter”
- The ‘Add Python Interpreter’ window should appear. Choose your Python Interpreter (for example: "Base Python: Python 3.11.4") and click ‘Okay’. A virtual Environment should be created.
- In the terminal in PyCharm, you want to install pyside6 using the command ‘pip install pyside6’ and then ‘pip install python-vlc’
- Once installed, you should be able to run BestNotes.

#### IntelliJ instructions
PDF Instructions: [Best Notes Windows Set Up Instructions.pdf](https://github.com/user-attachments/files/17853642/BestNotes.Windows.Set.Up.Instructions.pdf)

-	First if you don't have IntelliJ, download the IDE IntelliJ, then clone the project into IntelliJ using the repository URL
-	Then download and install VLC media player: https://www.videolan.org/vlc/
-	Ensure you have the python plugin downloaded in the IDE
-	When you try to run main.py, it will give a warning about a missing translator. So, click configure python interpreter and then add a new local interpreter.
-	Then on the left bar make sure that Virtualenv Environment is selected. 
-	Inside the Virtualenv Environment menu create a new environment and click ok.
-	Open command prompt and cd IdeaProjects\BestNotes (or where ever you saved the repository)
-	Afterwards type in the command venv\Scripts\activate
-	Next, you want to install pyside6 using the command ‘pip install pyside6’ and then ‘pip install python-vlc’
-	After it’s installed, you can exit the command prompt and go back to IntelliJ
-	Finally, run main.py and the application should pop up.

### Mac Instructions
PDF Instructions: [Best Notes Mac Set Up Instructions.pdf](https://github.com/user-attachments/files/17577744/Best.Notes.Mac.Set.Up.Instructions.pdf)

For mac, you will need to download the IntelliJ IDE. You can download it from the following link:
https://www.jetbrains.com/idea/download/?section=mac

You will also need to download python, ideally the latest version. You can download it from the following link:
https://www.python.org/downloads/

Also make sure to install VLC media player: https://www.videolan.org/vlc/

Once that is completed, do the following:
- Clone the repository into IntelliJ. Make sure you do not save the repository to your desktop (Documents is an easy choice)
- Open the repo in Intellije and navigate to main.py. In the upper right hand corner a link saying "Configure Python Interpreter" should appear. Clicking it will allow you to set up a virtual environemnt to run the project.
- In the dialogue box an option to choose the SDK (Software Development Kit) should be present and say <No Project SDK>. Click this and choose "Add python SDK from disk"
- In the new diaglogue box, to the left the top option, "Virtualenv Environment" should be highlighted. New environment should be checked. In the Base Interpreter Box, it will automatically navigate to the version of python you downloaded, but if not, click the three dots next to the box to manually navigate to it. It will typically be under /usr/local/bin/python3.13 for example, if you downloaded python 3.13. Clicking OK will create the virtual environment.
- Once this is done, navigate to terminal by clicking the magnifying glass on the right side of the menu bar, type "terminal" and enter, which will open terminal.
- Now you need to navigate to the project. You can do this using the command, "cd Documents", followed by "cd 01-BestNotes" or cd and the name you chose for the cloned repository.
- Once you have navigated to BestNotes, type "source venv/bin/activate" to activate the virtual environment. It should say (venv) (base) <rest of command line>
- Now type the command "pip3 install pyside6" and "pip install python-vlc" after.
- Once the download has completed, return to IntelliJ and hit the play button to run main.py and use the application.

Credits: Contributing on the code from [WhiteBoard](https://github.com/Shabbar10/PySide-Whiteboard)

