package com.example.cineaste

fun getFavoriteMovies(): List<Movie> {
    return listOf(
        Movie(1,"Pride and prejudice",
            """Sparks fly when spirited Elizabeth Bennet meets single, rich, and proud
                    Mr. Darcy. But Mr. Darcy reluctantly finds himself falling in love with a woman
                    beneath his class. Can each overcome their own pride and prejudice?""",
        "16.02.2005.","https://www.imdb.com/title/tt0414387/",
            "https://image.tmdb.org/t/p/original/sGjIvtVvTlWnia2zfJfHz81pZ9Q.jpg",
            "https://image.tmdb.org/t/p/original/pgEWI7yGYF0mM5Uf1YxSOHsBkJR.jpg"),

        Movie(2,"Interstellar","""When Earth becomes uninhabitable in the future, 
            a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, 
            to find a new planet for humans.""","7.11.2014","https://www.imdb.com/title/tt0816692/",
            "https://image.tmdb.org/t/p/original/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            "https://image.tmdb.org/t/p/original/9REO1DLpmwhrBJY3mYW5eVxkXFM.jpg"),

        Movie(3,"Big Momma's House","""In order to protect a beautiful woman and her son from a robber, a male
             FBI agent disguises himself as a large grandmother.""","31.05.2000",
            "https://www.imdb.com/title/tt0208003/",
            "https://image.tmdb.org/t/p/original/nudrKU6mwJOMtZIIyr2fKO1NcDt.jpg",
            "https://image.tmdb.org/t/p/original/dDIwGI48DRBzmLql1zHEmgQQ1Rk.jpg"),

        Movie(4,"Casino Royale","""After earning 00 status and a licence to kill, secret agent James Bond sets out on 
            his first mission as 007. Bond must defeat a private banker funding terrorists in a high-stakes game of poker at 
            Casino Royale, Montenegro.""", "16.11.2006","https://www.imdb.com/title/tt0381061/",
            "https://image.tmdb.org/t/p/original/lMrxYKKhd4lqRzwUHAy5gcx9PSO.jpg",
            "https://image.tmdb.org/t/p/original/eMPmUCfYkSxtTm6Zdyv904QpqF4.jpg"),

        Movie(5,"Cool Runnings","""When a Jamaican sprinter is disqualified from the Olympic Games, he enlists
             the help of a dishonored coach to start the first Jamaican Bobsled Team.""","1.10.1993",
            "https://www.imdb.com/title/tt0106611/",
            "https://image.tmdb.org/t/p/original/6fXuGEb7EqGmAeUodxm7l5ELPZ.jpg",
            "https://image.tmdb.org/t/p/original/r8b8oIedAYh2l2xD2woSmvO2cv3.jpg")
    )
}
fun getRecentMovies(): List<Movie> {
    return listOf(
        Movie(1,"Furiosa: A Mad Max Saga",
            """The origin story of renegade warrior Furiosa before her encounter and
                    teamup with Mad Max.""",
            "24.05.2024.","https://www.imdb.com/title/tt12037194",
           " https://image.tmdb.org/t/p/original/iADOJ8Zymht2JPMoy3R7xceZprc.jpg",
            "https://image.tmdb.org/t/p/original/shrwC6U8Bkst9T9J7fr1A50n6x6.jpg"),

        Movie(2,"Oppenheimer","""The story of American scientist J. Robert Oppenheimer and his role in 
            the development of the atomic bomb.""","21.07.2023","https://www.imdb.com/title/tt15398776/",
            "https://image.tmdb.org/t/p/original/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
            "https://image.tmdb.org/t/p/original/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg"),

        Movie(3,"Meg 2: The Trench","""A research team encounters multiple threats while exploring the depths of 
            the ocean, including a malevolent mining operation.""","4.08.2023",
            "https://www.imdb.com/title/tt9224104/",
            "https://image.tmdb.org/t/p/original/4m1Au3YkjqsxF8iwQy0fPYSxE0h.jpg",
        "https://image.tmdb.org/t/p/original/5mzr6JZbrqnqD8rCEvPhuCE5Fw2.jpg"),

        Movie(4,"Glass Onion: A Knives Out Mystery","""Tech billionaire Miles Bron invites his friends for a 
            getaway on his private Greek island. When someone turns up dead, Detective Benoit Blanc is put on the case.""",
            "10.09.2022","https://www.imdb.com/title/tt11564570/",
            "https://image.tmdb.org/t/p/original/vDGr1YdrlfbU9wxTOdpf3zChmv9.jpg",
            "https://image.tmdb.org/t/p/original/y3uOfZAYwLkbvhunswBCskNMrfI.jpg"),

        Movie(5,"After Everything","""After breaking up with his true love, best-selling author Hardin Scott 
            travels to Portugal in an attempt to make amends for his past behavior.""","13.09.2023",
            "https://www.imdb.com/title/tt15334488/",
            "https://image.tmdb.org/t/p/original/uQxjZGU6rxSPSMeAJPJQlmfV3ys.jpg",
            "https://image.tmdb.org/t/p/original/j76bFSkkQxh66AqehhoqRljgeld.jpg")
    )

}
