function scrollToBookSection(){
    if(window.location.pathname === "/"){
        document.documentElement.scrollTo(
            {
                top:1500,
                behavior:"smooth"
            }
        );
    }
    else{
        window.location.replace("/reservation");
    }
}

function scrollToContactsSection(){
    if(window.location.pathname === "/"){
        document.documentElement.scrollTo(
            {
                top:2000,
                behavior:"smooth"
            }
        );
    }
    else{
        window.location.replace("/");
    }
}
function scrollToAboutUsSection(){
    if(window.location.pathname === "/"){
        document.documentElement.scrollTo(
            {
                top:800,
                behavior:"smooth"
            }
        );
    }
    else{
        window.location.replace("/about-us");
    }
}

function redirectToDoubleRoom(){
    window.location.replace("/rooms/double-room")
}
function redirectToTripleRoom(){
    window.location.replace("/rooms/triple-room")
}
function redirectToApartment(){
    window.location.replace("/rooms/apartment")
}