# GwentStone Lite

**GwentStone Lite** is a Java-based, turn-based card game inspired by *Gwent* and *Hearthstone*.  
Built to explore **object-oriented programming (OOP)**, focusing on inheritance, polymorphism, file handling, and game logic implementation.

---

## Gameplay Overview

### Player Setup
Each player starts with several decks to choose from.  
At the beginning of a game, the deck is shuffled using a seeded random shuffle (for deterministic results).  
Each player also receives a random hero with a unique ability. The first player is decided by the input.  

Decks can be reused across multiple matches: once a game ends, a deck must be restored to its original state, exactly as before shuffling, so players can pick it again.

---

### Game Start
- At the beginning of each round, both players draw the top card of their deck into their hand.  
- Players also gain mana: starting with **1** in round one, then incrementing by 1 per round until it caps at **10**. Mana is cumulative (unused mana carries over).  
- Each round consists of two turns (one for each player).  

---

### Turn Actions
During their turn, a player can:
- **Place cards** on the board (only if mana cost allows and row space is available).  
- **Attack with minions**, targeting enemy cards or the enemy hero.  
- **Use minion abilities** (like swap health, freeze, debuff, or heal allies).  
- **Use hero abilities** that target entire rows (e.g., freeze, destroy highest-health minion, or buff allies).  

Invalid actions (e.g., not enough mana, row full, attacking wrong target) are caught and reported with clear error messages.  

---

### Board
- The board is a **4×5 grid**.  
  - Rows 0–1 belong to Player 2.  
  - Rows 2–3 belong to Player 1.  
- Each row can contain up to 5 minions, aligned left when slots open up.  
- Tank cards force the opponent to target them before any other minions or the hero.  

---

### Combat
- Minions attack once per turn, dealing damage equal to their attack.  
- If a minion’s health reaches 0, it is removed from the board.  
- Minions with abilities (Disciple, The Ripper, Miraj, The Cursed One) can use special effects instead of a normal attack.  
- Frozen cards cannot attack until the next turn.  

If the enemy hero’s health reaches 0, the attacking player wins immediately.  

---

### Debugging & Stats
The system also supports special debug commands:
- Show cards in hand, in deck, or on the board.  
- Show active player, hero info, or a specific card at coordinates.  
- Track statistics: number of matches played and won by each player.  

---

## Implemented Features

The following functionalities were personally implemented:

- Game initialization: deck selection, shuffling with a seed for deterministic results, hero assignment, and starting player determination.  
- Placing cards from hand onto the board according to mana cost and row rules.  
- Handling invalid card placement, such as insufficient mana or trying to place a card on a full row.  
- Attacking opponent’s cards: applying damage and removing cards whose health reaches zero.  
- Handling invalid attacks: attacking with frozen cards, cards that already attacked, or targeting the wrong cards.  
- Using special abilities of cards according to their type and rules.  
- Handling invalid ability usage: using abilities with frozen cards, on wrong targets, or if the ability was already used in the current turn.  

---

## Technical Highlights
- Implemented in **Java**, focusing on **OOP design principles** (inheritance, encapsulation, polymorphism).  
- Clear separation of responsibilities between game entities (Board, Player, Hero, Minion).  
- Validation for all actions to handle edge cases and illegal moves.  
- Input-driven AI simulation that makes testing and debugging straightforward.  
- JSON input/output handling using **Jackson** for deterministic game state and results.  

---
