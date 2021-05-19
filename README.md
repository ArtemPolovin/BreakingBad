For building the application I used Clean architecture, MVVM, Retrofit, Coroutines, Dependency injection, Navigation component. 
In the repository I call method from retrofit and receive Response. I check the error response and put the result in the Resource class.
I call getBreakingBadCharacters() from repository in GetBreakingBadCharactersUseCase class. 
And by GetBreakingBadCharactersUseCase class I receive data from network request in ViewModel
In ViewModel I receive network data and put the data to LiveData. All UI data I receive in Fragment by LiveData.
In ViewModel I have two lists with Breaking Bad Characters. One list contains all Characters and other list contains filtered Characters.
If the user filters the list by name or season, then the given data is sent to the viewmodel the list is filtered and the filtered list is sent to the adapter
