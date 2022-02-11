# VelocitySafe
A Velocity plugin to manage whitelist on a proxy.

# Command
| Command                           | Description                        |
|-----------------------------------|------------------------------------|
| `/vwhitelist add <PlayerName>`    | Add the player to the whitelist    |
| `/vwhitelist remove <PlayerName>` | Remove the player to the whitelist |
| `/vwhitelist on`                  | Enable whitelist                   |
| `/vwhitelist off`                 | Disable whitelist                  |
| `/vwhitelist list`                | Show registered players            |
| `/vwhitelist reload`              | Reload config file                 |

# Config
The config file will be generated in the root directory of Velocity

`name: whitelist_safe.json`

# Warning
If you don't want to whitelist your backend servers and manage them only with this plugin,
we strongly recommend using `Velocity modern forwarding` or `BungeeGuard`, or strengthening your firewall settings!

# Todo
- Enhanced TAB completion for remove command.
- Changing the timing of UUID acquisition in Bedrock and Java