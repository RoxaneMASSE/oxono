# Oxono - Jeu de Stratégie

## Description

**Oxono** est un jeu de stratégie tour par tour innovant qui combine les mécaniques de déplacement et de placement de pièces. Les joueurs doivent aligner 4 pièces consécutives (soit par symbole X/O, soit par couleur) pour remporter la partie.

Le projet propose une **interface graphique moderne** (JavaFX) avec drag & drop.

## Règles du Jeu

### Principe de Base
- **2 joueurs** : Rose (PINK) et Noir (BLACK)
- **Plateau** : Grille configurable (4x4, 6x6, 8x8)
- **Pièces** : 2 Totems mobiles (X et O) + Jetons des joueurs

### Déroulement d'un Tour
1. **Phase MOVE** : Le joueur déplace un totem (X ou O) sur le plateau
2. **Phase INSERT** : Le joueur place un jeton correspondant au symbole du totem déplacé

### Conditions de Victoire
Aligner **4 pièces consécutives** en ligne droite :
- **Par symbole** : 4 X ou 4 O consécutifs
- **Par couleur** : 4 pièces de même couleur consécutives

## Architecture MVC

Le projet utilise une **architecture MVC rigoureuse** avec plusieurs **design patterns** :

### Design Patterns Implémentés

**Command Pattern** : Gestion complète de l'historique avec undo/redo via `CommandManager`, `MoveTotemCmd` et `InsertTokenCmd`

**Strategy Pattern** : IA modulaire avec `StrategyRandom` (mouvements aléatoires) et `StrategyMiniMax` (algorithme avancé)

**Observer Pattern** : Synchronisation automatique entre modèle et vue via `Observable`/`Observer`

## Intelligence Artificielle

### Deux Niveaux d'IA
- **Niveau Facile** : StrategyRandom - Mouvements aléatoires intelligents pour débutants
- **Niveau Difficile** : StrategyMiniMax - Algorithme avec anticipation sur 4 coups, optimisé pour performance

L'IA s'exécute de manière asynchrone pour éviter le blocage de l'interface utilisateur.

## Interface JavaFX

### Fonctionnalités Principales
- **Drag & Drop intuitif** pour déplacer les totems
- **Validation visuelle temps réel** (cases vertes/rouges)  
- **Menu complet** : Nouveau jeu, Undo/Redo, Abandon
- **Gestion d'état** : Interface adaptée aux phases MOVE/INSERT
- **Compteurs en temps réel** avec indicateur du joueur actif

### Utilisation
- **Déplacer totem** : Glisser-déposer vers case valide
- **Placer jeton** : Clic sur case adjacente au totem  
- **IA** : Bouton "AI play" disponible au tour de l'IA

## Configuration

**Tailles de plateau** : 4x4, 6x6 (défaut), 8x8
**Modes de jeu** : Joueur vs Joueur, Joueur vs IA (2 niveaux)
**Interface** : JavaFX moderne avec interactions avancées

---

*Projet développé avec architecture MVC + Design Patterns - Interface JavaFX moderne*
