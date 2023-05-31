Scénario

Vous avez lancé votre entreprise en développement d'applications il y a quelques années et celle-ci rencontre un franc succès. Elle a fait de vous un développeur émérite et reconnu dans le monde entier.
Vous êtes sollicité par de nombreuses sociétés internationales. Cela tombe bien, vous souhaitez voir du pays et décidez d’émigrer à New York City pour rejoindre un grand nom de l’immobilier (et au passage déguster quelques délicieux cheesecakes).
Cette grande agence new-yorkaise est spécialisée dans la vente de biens d’exception : duplex, lofts, penthouse et manoirs. Jusqu’à présent, elle a fonctionné selon les bonnes vieilles techniques du 19ème siècle : des dossiers papier et un crayon. Il est grand temps de s’inscrire dans le digital (et de préserver les forêts). Le board vous demande donc de développer une application mobile Android permettant aux agents de pouvoir accéder aux fiches des différents biens immobiliers depuis leur équipement mobile.
Après plusieurs jours de brainstorming et d’intense réflexion, le nom retenu pour l’application est Real Estate Manager.


Design de l'application

La maquette du stagiaire n’est qu’un exemple : vous êtes totalement libre de poursuivre la conception et l'implémentation de l’application selon vos goûts et vos envies. Soyez fou, lancez-vous (avec ou sans parachute), utilisez les couleurs et les dispositions de votre choix.
Il faudra néanmoins respecter au maximum les directives et les recommandations du Material Design de Google, afin de garantir la meilleure expérience utilisateur possible. Heureusement, vous ne serez pas seul dans cette aventure, Google a pensé à tout, en vous proposant :
Un outil web permettant de sélectionner les couleurs de votre application ;
Un moteur de recherche d’icônes, pour télécharger des icônes sous différents formats ;
Et d’autres contenus que vous découvrirez au fur et à mesure.
L’application doit fonctionner à la fois sur smartphone et tablette, il faut donc prévoir un affichage adapté sur ces deux équipements. L'écran pourra donc être divisé par deux sur tablette (la liste des biens à gauche et le détail d'un bien à droite) alors que sur smartphone, un écran à la fois sera affiché (la liste des biens en plein écran ainsi que le détail d'un bien en plein écran également).




Fonctionnalités demandées

Amélioration des fonctionnalités existantes
Trois fonctionnalités ont déjà été implémentées dans l'application pré-développée par le stagiaire et placées dans la classe Utils.java :
Conversion d'un prix d'un bien immobilier (Dollars vers Euros) :
Ce que vous devez réaliser : Sur le même modèle que la méthode existante, une seconde méthode devra être créée afin de gérer la conversion de l'Euro vers le Dollar.
Le test à produire : Cette méthode devra être validée par un test unitaire.
Conversion de la date d'aujourd'hui en un format plus approprié :
Ce que vous devez réaliser : Pour le moment, la méthode retourne la date d'aujourd'hui au format "2018/01/15". Nous souhaitons maintenant qu'elle retourne une valeur au format "15/01/2018".
Test à produire : Cette méthode devra être validée par un test unitaire.
Vérification de la connexion réseau :
Ce que vous devez réaliser : Pour le moment, la méthode vérifie uniquement si le Wifi est activé afin d'identifier si une connexion internet est présente. Cela ne fonctionne pas très bien, et vous devrez proposer une solution plus pérenne.
Test à produire : Cette méthode devra être validée par un test d'intégration.
Ces trois fonctionnalités sont donc à améliorer et doivent absolument être validées par des tests (unitaires ou d'intégrations en fonction des cas). N'oubliez pas de garder la classe Utils.java pour présenter ces améliorations lors de la soutenance.


Mode hors-ligne
Les agents immobiliers étant toujours en déplacement, parfois dans des zones non couvertes par un réseau cellulaire ou Wi-Fi, il est obligatoire que l’application fonctionne en mode hors-ligne. Il faut donc prévoir que l'ensemble des données sera stocké dans la base de données de l’application.
Les données devront être stockées dans une base de données SQLite. Les données devront être également accessibles en lecture en utilisant une couche d'abstraction du type ContentProvider.

Attributs d'un bien immobilier
Pour chaque bien immobilier, les informations suivantes doivent être disponibles :

Le type de bien (appartement, loft, manoir, etc) ;
Le prix du bien (en dollar) ;
La surface du bien (en m2) ;
Le nombre de pièces ;
La description complète du bien ;
Au moins une photo, avec une description associée. Vous devez gérer le cas où plusieurs photos sont présentes pour un bien ! La photo peut être récupérée depuis la galerie photos du téléphone, ou prise directement avec l'équipement ;
L’adresse du bien ;
Les points d’intérêts à proximité (école, commerces, parc, etc) ;
Le statut du bien (toujours disponible ou vendu) ;
La date d’entrée du bien sur le marché ;
La date de vente du bien, s’il a été vendu ;
L'agent immobilier en charge de ce bien.
Gestion des biens immobiliers
L’agent immobilier doit pouvoir créer un nouveau bien depuis l’application, en précisant tout ou partie des informations demandées.
Une fois l'ajout d'un bien correctement effectué, un message de notification doit apparaitre sur le téléphone de l'utilisateur afin de lui indiquer que tout s'est bien passé.
La géo-localisation d'un bien est automatiquement effectuée à partir de son adresse, afin d'afficher la vignette de carte correspondante dans le détail du bien. Pour ce faire, regardez du côté de la Static Maps API ou de la Lite Mode Maps Android API.
Les biens existants peuvent être édités pour mettre à jour leurs informations (ajout, modification, suppression).
Il n’est pas possible de supprimer un bien, en revanche il est possible de préciser qu’un bien a été vendu, en précisant obligatoirement sa date de vente.

Géo-localisation
Si l'agent immobilier est connecté et géo-localisable, il peut afficher les biens sur une carte, afin de voir d'un coup d'œil les biens les plus proches de lui. Cette carte est dynamique : l'agent peut zoomer, dézoomer, se déplacer, et afficher le détail d'un bien en cliquant sur la punaise correspondante.

Moteur de recherche
L’agent immobilier peut effectuer une recherche multi-critères sur l’ensemble des biens immobiliers de la base. Par exemple :

Afficher les appartements d’une surface comprise entre 200 et 300m2, proches d’une école et des commerces, mis sur le marché depuis moins d’une semaine ;
Afficher les maisons vendues au cours des trois derniers mois, dans le secteur de Long Island, avec au moins trois photos, pour un prix compris entre $1,500,000 et $2,000,000. 
Fonctionnalité complémentaire
Vous avez très probablement une tonne d’idées pour améliorer l’application existante, comme :

Créer un simulateur de prêt immobilier, avec un apport, un taux et une durée ;
Intégrer des vidéos dans les informations des biens ;
Synchroniser l'ensemble des biens sur un Back-end comme Firebase ;
Choisissez la fonctionnalité de votre choix parmi les trois proposées puis développez-la.

