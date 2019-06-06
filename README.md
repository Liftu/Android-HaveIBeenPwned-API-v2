# Android-HaveIBeenPwned-API
Just an Android interface to the Have I Been Pwned API

## Presentation
Une application toute simple utilisant l'API de site Have I Been Pwned afin d'afficher la liste des differents leaks ayants été référancé.

## Consignes
- Le minimum :
  - Deux écrans : Un écran avec une liste et un écran avec un détail de l’item (Fait)
  - Appel WebService à une API Rest (Fait)
  - Stockage des données en cache (Fait)
- Les plus :
  - Architecture (MVC ou MVP ou MVVM) (Fait)
  - Gitflow (Fait)
  - Animation entre écrans
  - Notifications Push (Firebase)
  - Autres fonctionnalités (Fait : barre de recherche + toasts)

## Fonctionnalites
### Ecran principal :
- Affichage de la liste des leaks sous forme de cartes les unes a la suite des autres
- Affichage d'une barre de chargement le temps que la requete s'effectue

<img src="readme-img/screen_loading.png" alt="loading">

- Utilisation d'une barre de recherche permettant de filtrer les leaks par titre ou nom de domaine.

<img src="readme-img/search_bar.png" alt="search bar">

- Utilisation du cache permettant de stocker la liste des leaks, afin de les afficher meme sans connexion (L'application affiche un toast pour savoir si l'application a reussit a requeter l'API ou non)

<img src="readme-img/connection_success.png" alt="connection success"> <img src="readme-img/connection_failure.png" alt="connection failure">

### Ecran secondaire
- Affichage global toujours sous forme d'une carte
- Affichage du logo, titre, nom de domaine, nombre de données leakés, date du leak et les types de données.
  - Les types de données sont affichés via une ListView afin de pouvoir scroller s'il y en a trop pour etre affiché a l'écran
  
<img src="readme-img/second_activity.png" alt="second activity"> <img src="readme-img/second_activity_list_view.png" alt="second activity list view">
