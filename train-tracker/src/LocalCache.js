const LOCAL_CACHE="LOCAL_CACHE"

const getLocalCache=()=>{

    let localCache={
        data:{},
    }  

    try {
        const data=localStorage.getItem(LOCAL_CACHE)

        if(data){
            localCache=JSON.parse(data)
        }
    }
    catch(e){
        console.error(e.message)
    }

    return localCache
}

export const setToCache=(value)=>{

    const localCache=getLocalCache()
    let data=localCache.data

    const item={
        local:value
    }

    data[value]=item

    try{
        localStorage.setItem(LOCAL_CACHE,JSON.stringify(localCache))
    }
    catch(e){
    }

}

// const confirmCheckIn = (username, password) =>{
//     const localUser = localStorage.getItem("USERNAME");
//     const localPass = localStorage.getItem("PASSWORD");
//     if(localUser===null||localPass===null){
//         alert("No current user detected for this device, please sign in or create a new account");
//     }
// }