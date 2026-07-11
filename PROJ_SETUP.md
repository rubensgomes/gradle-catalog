# Create GitHub Project

Personal checklist for creating a new Java/Kotlin Gradle project and
publishing it to GitHub. Steps are ordered so each phase produces
something the next phase consumes.

## 1. Install prerequisites

### Java + Gradle (cross-platform, via SDKMAN)

```shell
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java          # latest LTS by default
sdk install gradle        # only needed for `gradle init` below
```

SDKMAN works on macOS, Linux, and WSL — no symlink fiddling. If you
prefer Homebrew (macOS-only):

```shell
brew install openjdk gradle
sudo ln -sfn "$(brew --prefix openjdk)/libexec/openjdk.jdk" \
             /Library/Java/JavaVirtualMachines/openjdk.jdk
```

### GitHub access

- [Generate an SSH key and add it to ssh-agent](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent)
- [Install the GitHub CLI](https://github.com/cli/cli)
- [Create a Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)
  with `repo` and `write:packages` scopes.

## 2. Create the local Gradle project

```shell
PROJ_NAME="<add-proj-name>"  # e.g., gradle-catalog
mkdir "$PROJ_NAME" && cd "$PROJ_NAME"
gradle init                  # follow the interactive prompts
```

See [Initializing the Project](https://docs.gradle.org/current/userguide/part1_gradle_init.html)
for guidance on the `gradle init` options.

Once the wrapper (`gradlew`) is generated, use `./gradlew` from here on
and (optionally) remove system Gradle.

## 3. Publish to GitHub

```shell
git init -b main
git add .
git commit -m "initial commit"
gh repo create --homepage "https://github.com/rubensgomes" --public "$PROJ_NAME"
git remote add origin "https://github.com/rubensgomes/$PROJ_NAME"
git push -u origin main
```

## 4. Post-creation

If the project will use the `net.researchgate.release` plugin, create
the `release` branch that the plugin pushes to:

```shell
git checkout -b release
git push -u origin release
git checkout main
```

If the project's CI will publish to GitHub Packages, add the PAT to
repo secrets (used by `.github/workflows/release.yml`):

```shell
gh secret set RUBENS_PAT_TOKEN --body "<your-PAT>"
```

---
Author: [Rubens Gomes](https://rubensgomes.com/)
