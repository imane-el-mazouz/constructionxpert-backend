# ConstructionXpert Services

## Contexte du projet

ConstructionXpert Services est une solution moderne pour la gestion de projets dans le domaine de la construction. L'objectif est d'assurer la qualité du code, d'automatiser la construction des microservices, et de gérer le déploiement des images Docker sur un registre sécurisé.

## Objectifs

- Intégration des tests unitaires dans le pipeline CI pour chaque microservice.
- Analyse de la qualité de code avec **SonarQube**.
- Automatisation de la construction des images Docker à chaque commit.
- Pousser les images Docker vers un registre sécurisé (**DockerHub**).

## Technologies utilisées

- **Java/Spring Boot**.
- **JUnit**, **Mockito** pour les tests unitaires.
- **SonarQube** pour l'analyse de la qualité de code.
- **Jenkins** pour l'automatisation CI/CD.
- **Docker** pour la conteneurisation des microservices.
- **DockerHub** comme registre d'images Docker.

## Prérequis

- Jenkins installé et configuré sur le serveur.
- Docker installé sur le serveur Jenkins.
- SonarQube installé et configuré.
- Accès à un registre Docker (par exemple, DockerHub).
- Accès au référentiel Git contenant le code source.

## Étapes de configuration

### 1. **Configuration des tests unitaires**
   - Utiliser des frameworks comme JUnit (Java) ou Jest (Node.js) pour écrire des tests unitaires.
   - Ajouter les tests unitaires dans le code des microservices.
   - Assurez-vous que tous les microservices peuvent exécuter les tests via la commande appropriée (par exemple, `mvn test` pour Maven).

### 2. **Configuration de SonarQube**
   - Assurez-vous que SonarQube est correctement configuré pour analyser la couverture de code, les bugs, et les vulnérabilités.
   - Ajouter un fichier `sonar-project.properties` dans chaque microservice pour configurer les paramètres SonarQube :
     ```properties
     sonar.projectKey=<project-key>
     sonar.host.url=http://<sonarqube-server>
     sonar.login=<token>
     ```
   - Configurer le pipeline Jenkins pour utiliser le **SonarQube Scanner Plugin**.

### 3. **Configuration de Jenkins**
   - Créer un pipeline Jenkins multibranches pour surveiller la branche `main`.
   - Ajouter les étapes suivantes au pipeline Jenkins :
     - **Exécution des tests unitaires** :
       ```sh
       mvn clean test
       ```
     - **Analyse SonarQube** :
       ```sh
       sonar-scanner
       ```
     - **Construction des images Docker** :
       ```sh
       docker build -t <docker-image-name>:<tag> .
       ```
     - **Push des images Docker vers DockerHub** :
       ```sh
       docker login -u <username> -p <password>
       docker push <docker-image-name>:<tag>
       ```

### 4. **Automatisation des builds avec Jenkins**
   - Configurez Jenkins pour surveiller la branche `main` et déclencher automatiquement le pipeline à chaque commit.
   - Intégrer les tests unitaires dans le pipeline pour garantir que le code est testé avant la construction de l'image Docker.
   - Ajouter un seuil de qualité dans SonarQube pour empêcher la construction si la couverture de code est insuffisante ou si des bugs critiques sont détectés.

### 5. **Enregistrement des images Docker dans DockerHub**
   - Utiliser DockerHub pour héberger les images Docker. Configurez un compte et générez un token pour l'authentification automatique.
   - Assurez-vous que le pipeline Jenkins peut pousser les images Docker vers DockerHub après chaque build réussi.

## Exécution du pipeline Jenkins

1. **Trigger** : À chaque commit sur la branche `main`, Jenkins déclenche automatiquement le pipeline.
2. **Tests Unitaires** : Jenkins exécute les tests unitaires pour chaque microservice.
3. **Analyse de Qualité de Code** : Jenkins exécute l'analyse de SonarQube pour vérifier la qualité du code.
4. **Construction des Images Docker** : Jenkins construit les images Docker à partir des Dockerfiles des microservices.
5. **Pousser vers DockerHub** : Jenkins pousse les images Docker vers DockerHub si les tests unitaires et l'analyse SonarQube réussissent.




