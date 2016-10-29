# "Welcome! My Name is Aline."
======================
"I am a web app for managing the seminars of the company MaibornWolff. 
I help Users to create and maintenance seminars, see all available seminars and book them."

## "What kind of Files to we have?"
"I will tell you a little bit about our file-structure, so if you are new in this kind of business, 
this may help you to get along."

* `package.json`: "This file holds various metadata relevant to this project. This file is used to give information to `npm` to identify our project and handle its dependencies."

* `webpack.config.js` "As the name suggests, this is the configuration file for webpack. ([Webpack](http://webpack.github.io/docs/what-is-webpack.html) is our module bundler)"

* `bin/app.bundle.js` "the bundled code in one file. Thanks to webpack :)"

* `.gitignore` "I really hope you know this..."

## "How to see our code in action?"
"So you wanna see how this magic looks working? It's really easy. Just follow my instructions:"
1. `npm install`
2. Start the server with `npm run startServer`
3. Navigate to `localhost:8080` in your browser

## "You want to code React in the Intellij IDE?"
"That's great! But you need to do some things so IntelliJ knows
what you are doing. You should already have the `node_modules` folder
ready, containing our **React** packages. Otherwise run `npm install`.

Good. now you open the `preferences` in IntelliJ, navigate to `Languages and Frameworks`, choose `Javascript`.
Then under `libraries` you press `add`, and name it `React`. With the little `+` you choose
`node_modules/react/dist/react.js`. Another time pressing the little `+` and choose 
`node_modules/react-dom/dist/`. Save the thingie!


To help IntelliJ **recognize the JSX** (the kind of HTML Code in our Javascript Files)
you need to navigate back to `preferences` --> `Languages and Frameworks` --> `JavaScript` and 
there change the `Javascript language version` to `JSX Harmony`.
"
