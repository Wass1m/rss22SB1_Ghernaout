# REST SPRING API

Cette application sert d'API Spring REST pour consommer un flux RSS22

### Liens utils :

- Lien github du client : https://github.com/Wass1m/RSS22-Projet-Client.git
- Lien vers CleverCloud : ghernmegh-projet.cleverapps.io

## STRUCTURE

#### Controllers
Ce package contient la classe contrôleur, elle permet de router les différentes requêtes GET,
POST, PUT et DELETE

#### Model
Ce package contient toutes les classes de modèle qui construisent la classe Article et la classe
Feed, ensemble elles forment le modèle RSS22 spécifié par le schéma XSD.
#### Repositories
Ce paquet contient le référentiel qui permet de gérer le stockage de la base de données de
tous les Articles existants et de sauvegarder, insérer et supprimer tout Article
#### Utils
Ce package contient des fonctions utiles a la validation du Schema XSD, conversion XML
HTML etc...
#### Exception
Ce package contient les classes d’exception, elles gèrent notamment la classe d’exception du
flux non valide etc..

#### Resources
Le répertoire des ressources contient : 
- Un répertoire "xml" qui contient les fichiers de
validation xsd et xslt ainsi que les fichiers xml de test, et le json POSTMAN.
- Un répertoire "templates" qui contient les pages web "index" et "help" au format HTML.
- Un répertoire "static" qui contient les images utilisées dans les pages web.


## Scripts

Quand vous cloner le projet il suffira des commandes suivantes :

### `mvn spring-boot:run`

Permettra de lancer le serveur
Ouvrez [http://localhost:8080](http://localhost:8080) pour le voir dans le browser.

### `.application.properties`

Ce fichier contient les variables d'environnement du serveur, il contient principalement la variable contenant l'URI MongoDB, le DBUG, et le PORT du serveur si jamais on veut les changer.
