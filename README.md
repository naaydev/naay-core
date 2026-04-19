# NaayCore

Plugin base para servidores Minecraft da rede Naay.

## Características

- Sistema de perfil de jogadores (coins, cash, kills, deaths, wins)
- Sistema de banco de dados MySQL
- Comandos úteis (/nick, /fly, /gm, /heal, /feed, /god, /vanish, /clear, /speed, /skins, /coins, /cash, /tell, /reply, /ping, /spawn, /lobby)
- NPCs para seleção de jogos
- Hologramas informativos
- Scoreboard automático
- PlaceholderAPI integration
- Sistema de filas para conexão
- Sistema de ranks base

## Comandos

| Comando | Permissão | Descrição |
|---------|----------|-----------|
| `/nick <nick>` | naay.core.nick | Mudar nick |
| `/fly` | - | Ativar/desativar voo |
| `/gm <0-3>` | naay.core.gamemode | Mudar modo de jogo |
| `/heal` | - | Restaurar vida |
| `/feed` | - | Restaurar fome |
| `/god` | - | Modo god |
| `/vanish` | naay.core.vanish | Vanish |
| `/clear` | - | Limpar inventário |
| `/speed <1-10>` | - | Velocidade |
| `/skins` | - | Ver skins disponíveis |
| `/coins` | - | Ver coins |
| `/cash` | - | Ver cash |
| `/tell <player> <msg>` | - | Mensagem privada |
| `/reply <msg>` | - | Responder mensagem |
| `/ping` | - | Ver ping |
| `/spawn` | - | Ir para o spawn |
| `/lobby` | - | Ir para o lobby |

## Instalação

1. Baixe o plugin
2. Coloque no diretório `plugins/` do seu servidor
3. Configure o `config.yml` com as credenciais do banco de dados
4. Reinicie o servidor

## Configuração

```yaml
database:
  type: mysql
  mysql:
    host: localhost
    port: 3306
    database: naaycore
    user: root
    password: ""

lobby: Lobby-1

queue:
  enabled: true
```

## Dependências

- ProtocolLib
- PlaceholderAPI (v2.11.5)

## Placeholders

| Placeholder | Descrição |
|------------|-----------|
| `%naay_coins%` | Coins do jogador |
| `%naay_cash%` | Cash do jogador |
| `%naay_kills%` | Kills do jogador |
| `%naay_deaths%` | Deaths do jogador |
| `%naay_wins%` | Vitórias do jogador |
| `%naay_level%` | Level do jogador |
| `%naay_xp%` | XP do jogador |

## API

```java
// Obter perfil do jogador
Profile profile = Profile.getProfile(player.getName());
profile.getCoins();
profile.setCoins(100);

// Enviar jogador para outro servidor
Main.sendServer(player, "SkyWars-1");
```

## Autores

- **NaayDev** - Criador original

## Suporte

Para suporte, entre em contato no Discord da comunidade.