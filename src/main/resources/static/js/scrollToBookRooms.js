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

function redirectToDoubleRoom(){
    window.location.replace("/rooms/double-room")
}
function redirectToTripleRoom(){
    window.location.replace("/rooms/triple-room")
}
function redirectToApartment(){
    window.location.replace("/rooms/apartment")
}