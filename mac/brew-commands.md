# Brew Commands 🔥

Here's a list of common `brew` (Homebrew) commands:

### General Commands
- `brew update` - Update Homebrew itself and all formulae.
- `brew upgrade` - Upgrade all outdated packages.
- `brew install <formula>` - Install a package (formula).
- `brew uninstall <formula>` - Uninstall a package (formula).
- `brew list` - List all installed formulae.
- `brew info <formula>` - Get information about a specific formula.
- `brew search <text>` - Search for available formulae.

### Maintenance Commands
- `brew cleanup` - Remove outdated versions of installed formulae.
- `brew doctor` - Check your system for potential problems.
- `brew autoremove` - Uninstall formulae that were only installed as dependencies.
- `brew update-reset` - Reset Homebrew's repository.

### Tap Commands
- `brew tap` - Add more repositories to the list of formulae that brew tracks, updates, and installs from.
- `brew untap <tap>` - Remove a tapped repository.

### Cask Commands
- `brew install --cask <cask>` - Install a GUI application or large binary.
- `brew list --cask` - List all installed casks.
- `brew uninstall --cask <cask>` - Uninstall a cask.
- `brew search --cask <text>` - Search for available casks.
- `brew upgrade --cask` - Upgrade all outdated casks.

### Analytics and Diagnostics
- `brew analytics` - Manage Homebrew's analytics (view and toggle).
- `brew config` - Show Homebrew and system configuration.
- `brew missing` - Show missing dependencies.

### Miscellaneous
- `brew pin <formula>` - Pin a formula, preventing it from being upgraded.
- `brew unpin <formula>` - Unpin a formula.
- `brew edit <formula>` - Edit a formula.
- `brew audit <formula>` - Check formulae for Homebrew coding style violations.
- `brew gist-logs <formula>` - Upload logs for a failed install to a Gist.
