Dartmouth Drinks
----------------

Created by Brian Tomasco, Alex Waterhouse, and Zack Johnson

Description of app and flow
---------------------------

This app helps the user track their drinking habits throughout an "on night" and 
retroactively by looking at past nights. 

A user can create an account or sign into an existing one. Once signed in, the 
user is greeted by a welcome screen. The welcome screen gives access to a user's
settings, history, and a resources page. A user can change their notification 
settings: a BAC threshold for notifications, whether they want to be notified, 
whether they want to notify their emergency contact number, and whether they 
want to notify event organizers. Event organizers are notified when a user is 
within the event's radius and above the event's BAC threshold, set when the 
event is created. Emergency contacts and organizers are notified via text 
message. The history activity shows the user all their past on nights. Selecting
an on night shows the drinks from the night and gives the user access to a graph
of their BAC from the night. The resources page shows users important 
information, such as the SNS number, 911, and Dartmouth's Good Sam policy. The 
welcome activity also has a button for a user to start an on night.

Starting an on night opens the add activity, with different icons to add 
different drinks and the user's BAC. This activity has access to the same 
history and resource pages. Users cannot change or access their settings while a
night is ongoing. Adding drinks changes user's BAC accordingly. When an on night
is ongoing, a service is also running. The service keeps track of the user's BAC
and location, sending out notifications and text messages according to the 
current user's settings. This service cannot be stopped by the user; it stops 
itself at 8 AM after being started. This keeps users from cheating the system by
closing the service. This relies on the honesty of the user in inputting drinks.

Testing the app
---------------

Our repo contains the apk for our app. This apk is not what would be the 
production version of the app, as it enables manual night cancellation for 
testing purposes. In the resources page, the "Emergency" text will stop the 
service and end the on night. This enables inputting multiple nights in a short
span of time to see the history page with multiple nights.

Bug fixes since presentation
----------------------------

At our presentation, Lily discovered a reproducible bug with closing the app 
while an on night was activated. Further investigation revealed that there were 
some minor issues in the app with sending intents properly and backstacking. A 
few slight changes, including starting some activities with result to get 
instructions to finish back, fixed these issues. Also made some minor cosmetic 
changes and some quality of life improvements to testing capacity.

Departures from proposal
------------------------

Some of this was explained in presentation, some was not. The proposal said we 
would tell users how their max BAC compared to that of other users, but we 
decided not to implement this in the end, as we heard from friends and agreed 
that it would turn into somewhat of a competition for users, encouraging users 
on the lower side to drink more rather than encouraging users on the higher side
to drink less. "Placing drinking in context" is instead implemented through the 
history activity: a user can look back at a night and see that their BAC was 
exceptionally high one night and see what a good BAC is to aim for and set their
threshold accordingly. We also did not implement sending profile pictures to 
event organizers; this was due to the limitations of users' cellular plans (i.e.
if a user's plan did not allow unlimited exchange of images, the notification 
system would not work properly).