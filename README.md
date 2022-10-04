# 🗞 Newsreader - Android App
Android Newsreader for Inholland Mobile Development minor

## Todo
#### General
- [x] The app must have an Android look and feel.
- [x] The app must have error handling implemented. The app should not crash when (for example) a network error occurs.
- [x] The package ID of the app is: nl.<lastname>.<firstname>. (e.g. nl.vanwijngaarden.koen)
- [x] The appname must include your student number. (e.g. student512345)
- [ ] Localization should be done using strings. No hardcoded strings should be visible in the code.
- [x] The app must support both light as dark mode

#### Users
- [ ] Allow the user to login and logout (remember to clear the credentials when the user performs a logout).
- [ ] Store the user’s credentials, so the user does not need to log in every time the app starts.
- [ ] Allow a user to register a new account.
- [ ] Allow the user to see a list of all his/her favorite articles (when the user is logged in)

#### Main page
- [x] The main page of the application must show a list or grid of articles; the newest article on top.
- [x] The list or grid must be implemented using a LazyColumn or some other lazy way. The backend will return articles in batches of 20.
- [ ] The list must gradually load new pages when the user reaches the bottom
- [ ] A placeholder should be visible for each image that will be fetched from the backend
- [x] Each article has various properties. At least the image and the title of the article must be shown in the list or grid.
- [ ] When the user selects an article in the list or grid, the app must navigate to the details page of that article.
- [ ] When the user is logged in, the articles in the list must show some indicator whether or not they are on the favorites (liked) list of the user.
- [x] Create a way for the user to refresh the articles on the main page (pull to refresh, or some other way to refresh)
- [ ] Show some indicator (spinner or progress bar) when the app is getting the articles, so the user knows that the app is doing something.

#### Details page
- [ ] The details page shows at least these details of an article: title, summary, image, full article url (which may reside behind a clickable View).
- [ ] The page must implement scrolling, in case the details of the article don’t fit the screen of the devices.
- [ ] The full article url must be clickable and open the url in a browser.
- [ ] The page must provide a way for the user to return to the main page (don’t navigate to a new instance of the main page, but go BACK to the previous page)
- [ ] When the user is logged in, he/she must be able to mark/unmark articles as favorites.

#### Optional features
- [x] Implement the new Material You of Android 12 (+1.5)
- [ ] Support an extra theme that also works well with both light and dark mode (+0.5)
- [ ] Store the account credentials securely using the Android AccountManager (+1.0) (http://developer.android.com/reference/android/accounts/AccountManager.html)
- [ ] Allow the user to filter the articles based on news feed (single and or multi select). (+1.0)
- [ ] On the details page, show all properties of an article (timestamp as readable human date, related articles, categories) (+0.5)
- [ ] On the details page, allow the user to select a category. When the user selects a category, a list of articles must be shown that all belong to that category. Clicking on an article in that list shows the details of that article. Navigation in the app becomes more complicated with this feature, but navigation must still be functional. (+2.0)  
- [x] Localize the app for another language. (+0.3)
- [ ] Allow the user to share an article, using the OS-provided sharing functionality. (+0.3)
- [ ] Implement activity transition animations (+0.3).
- [ ] Create a home-screen widget. The widget should show the latest article and should also show the image if the latest message has one. (+1.0). A bonus 1.0 if this is updated through a sync adapter.
- [ ] Create a nice app icon and splash screen. (+0.5)
