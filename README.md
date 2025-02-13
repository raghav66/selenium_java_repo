Prerequisites

Ensure that you have the following installed on your Windows system before proceeding:
Java Development Kit (JDK 17)
Git
Any IDE
Apache Maven


Downloading the Automation Framework Code

Clone the Repository:
git clone <repository_url>
Navigate to the Project Directory:
cd rb_automation


Running the Automation Framework

1. Open Terminal in  IDE:
Go to View → Tool Windows → Terminal.

2. Execute the Automation Tests:
Run the following command in the terminal:
mvn clean test -q -Psanity -DbrowserName=chrome -DtagName=@rb -DexecutionEnv=production -DclientName=rb

3. Monitor Execution:
You will see the execution logs in the terminal.



Viewing Execution Reports

After execution, reports are generated in the report folder.

1. Locate Reports:
Navigate to the rb_automation/reports directory.

2. Open HTML Reports:
Open index.html in a browser to view the execution report.

3. Open PDF Reports:
Locate and open the generated PDF report for a detailed summary.


