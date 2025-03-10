# selenium-test-suite

This multi-threaded Selenium Test Suite enables seamless parallel execution across multiple projects, with each project running
in its own thread. It loads project-specific locators from their respective properties files and allows passing data through environ-
ment variables for data-driven testing and configuration. Each project generates detailed reports organized by modules and test
cases, capturing failed test cases with screenshots in a well-structured directory. By using ThreadLocal, each thread maintains its
own WebDriver instance and test data to avoid conflicts. I also implemented the Page Object Model architecture to ensure main-
tainable and scalable test cases for each project. This test suite can be used to test multiple projects with the same reusable setup
and code, allowing easy integration of new projects in minutes while following best practices and maximizing reusability across
the codebase.