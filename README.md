
#Summary
This is a little app that can be deployed on app engine whose primary utility is for playing with pub/sub push 
subscriptions.

#to deploy
1. `gradle assemble`
1. `gcloud app deploy build/libs/pubsub-push-micronaut-1-all.jar --project=<projectId>`

#to post a message
`curl -H "Content-Type: text/plain" -d 'blah1' -X POST http://<projectId>.appspot.com/_ah/push-handlers/trial`