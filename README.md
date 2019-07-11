# The Shows!
The shows is a small project which fetch information from themoviedb.org API 

# API KEY
In order to run the app the following variable must be created in the `gradle.properties` located in `$USER/.gradle`
* `export THE_MOVIES_DB_API_KEY = your_api_key`

# Architecture 
The application follow as close as possible the SOLID principles and CLEAN architecture using MVVM to take advantage of 
the architecture components for Android as well as allowing more features to be added in the long run.

* Data module
    - Network 
* Domain module
    - Repository 
* App module 
    - UI 
    - ViewModels ( business logic )
    
# Unit Testing
The code provide for the majority of the logical code testing, some classes are still pending
If the architecture is know before hand is a good approach to use TDD using the RED GREEN REFACTOR cycle 

# Layouts
The layouts are separated in small ones for reuse

# UI state
As common preference I implemented States in views defined with sealed classes to provide more flexibility in the long run as the project grows.

The use of this is pattern is located in the main activity where according to the given 'state' it will update the layout.

```javascript
private fun getShows(page: Int) {
        activityViewModel.getShows(page).observe(this, Observer { state ->
            when (state) {
                is Loading -> {
                    showLoader()
                }
                is ErrorEx -> {
                    showErrorMessage()
                    Timber.d(tag, "message : ${state.msg} , code: ${state.code}")
                }
                is ErrorMessage -> {
                    updateErrorMessage(state.status_message)
                }
                is NotFoundMessage -> {
                    updateErrorMessage(state.status_message)
                }
                is Success -> {
                    showList()
                    activityViewModel.nextPage = state.page + 1
                    activityViewModel.totalPages = state.total_pages
                    renderShows(state.results)
                }
            }
        })
```

I also use this approach to create a ResponseType because the server has 200 responses with `Error`.

# EntityMappers
I used just one Entity Mapper to transform from one layer to another an object, given that we want to be able to change 
any layer without worrying of breaking something

# Dependency Injection with KOIN
Koin provides an easy way to implement dependency injection easy to read and understand.

# DataBinding
The Activity uses binding as well the items in the adapters, I used the two different ways to make use of them, but in 
personal I prefer passing viewModels and objects to the layouts.
Also, bindAdapter were created to be able to bind some of the values in the view.

# Infinite Scroll
The application uses live data and boolean variables to provide the infinite scroll, all this logic is applied in the  
RecyclerView, this can be improved with a cache mechanism and other improvements for enhance the experience.


 



    




