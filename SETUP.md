# setup

This file describes high-level setup instructions to create a software development project.

## GitHub Setup

### Set Up SSH Key Pair, CLI Tools, Access Token

- [Generating a new SSH key and adding it to the ssh-agent](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)

- [Install GitHub CLI tools](https://github.com/cli/cli)

- [Create a Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)

### Import Local Repo to GitHub

```shell
git init -b main
git add .
git commit -m "initial commit" -a
gh repo create --homepage "https://github.com/rubensgomes" --public <proj-name>
git remote add origin https://github.com/rubensgomes/<proj-name>
git push -u origin main
```

Then, go to the [repo](https://github.com/rubensgomes/gradle-catalog) and create
a `release` branch. Click on the `drop-down` to `View all branches` and create
the `release` branch from main.

## Set Up Java

- Install latest Java LTS (macOS):

   ```shell
   brew install java
   sudo ln -sfn /usr/local/opt/openjdk/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk.jdk
   ```

## Set Up Gradle

- Install latest Gradle (macOS):

   ```shell
   brew install gradle
   ```

### Create Gradle App Project

- [Initializing the Project](https://docs.gradle.org/current/userguide/part1_gradle_init.html)

### Configure ~/.gradle/gradle.properties

   ```text
   # credentials to deploy builds to Rubens maven repository.
   repsyUsername=rubensgomes
   repsyPassword=***

   # as systemProp to make them accessible from settings.gradle.kts
   systemProp.repsyUsername=rubensgomes
   systemProp.repsyPassword=***

   # credentials to login to Rubens Docker account
   dockerUsername=rubensgomes
   dockerPassword=***
   ```

## Private Maven `repsy.io` Repo

- Have an account setup at [repsy.io](https://repsy.io/mvn/rubensgomes)