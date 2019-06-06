# Android-HaveIBeenPwned-API
Just an Android interface to the Have I Been Pwned API

## Presentation
Une application toute simple utilisant l'API de site Have I Been Pwned afin d'afficher la liste des differents leaks ayants ete referance.

## Fonctionnalites
### Ecran principal :
- Affiche la liste des leaks sous forme de cartes les unes a la suite des autres
- Affiche une barre de chargement le temps que la requete s'effectue
<img src="readme-img/screen_loading.png" alt="loading">

- Utilisation d'une barre de recherche permettant de filtrer les leaks par titre ou nom de domaine.
<img src="readme-img/search_bar.png" alt="search bar">

- Utilisation du cache permettant de stocker la liste des leaks, permettant de l'afficher meme sans connexion (L'application affiche un toast afin de savoir si l'application a reussit a requeter l'API ou non)
<img src="readme-img/connection_success.png" alt="connection success"> <img src="readme-img/connection_failure.png" alt="connection failure">

### Ecran secondaire
- Affichage global toujour sous forme de carte
- Affichage du logo, titre, nom de domaine, nombre de données leakés, date du leak et les types de données.
  - Les types de données sont affichés via une ListView afin de pouvoir scroller si il y en a trop pour etre afficher a l'ecran
<img src="readme-img/second_activity.png" alt="second activity"> <img src="readme-img/second_activity_list_view.png" alt="second activity list view">
