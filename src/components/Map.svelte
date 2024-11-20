<script>
    import { onMount } from "svelte";
    import mapboxgl from 'mapbox-gl';
    import 'mapbox-gl/dist/mapbox-gl.css';

    export let latitude;
    export let longitude;
    var markers = [];

    let mapContainer;

    mapboxgl.accessToken = 'pk.eyJ1Ijoia2hpdGNoIiwiYSI6ImNtM2d1cXN4MTA5YWIya3B4Y3didnBxM3QifQ.GPCb_j31HQhkDYmqvwKgLg'; 

    let map;

    // Initialize the map when component is mounted
    onMount(() => {
        initMap();
    });

    // Initialize the map function
    function initMap() {
        map = new mapboxgl.Map({
            container: mapContainer,
            style: 'mapbox://styles/khitch/cm3ngdbhy003e01rzaq77e11i',
            center: [longitude || -75.1652, latitude || 39.9526],
            zoom: 13
        });
    }

    // Update map position
    $: if (latitude && longitude) {
        updateMap(longitude, latitude);
    }

    // Function to update the map's position
    function updateMap(longitude, latitude) {
        if (markers!==null) {
            for (var i = markers.length - 1; i >= 0; i--) {
                markers[i].remove();
            }
        }

        if (map) {
            map.setCenter([longitude, latitude]);
            map.setZoom(13);

            // Create a marker for the user's current location
            var homemarker = new mapboxgl.Marker({ color: 'blue' })
                .setLngLat([longitude, latitude])
                .addTo(map);
                markers.push(homemarker);

            var homepopup = new mapboxgl.Popup({ offset: 25 })
                .setLngLat([longitude, latitude])
                .setHTML('<h3>You are here</h3>')
                .addTo(map);
                markers.push(homepopup);
        }
    }
</script>

<div bind:this={mapContainer} style="height: 100%; width: 100%"></div>

<style>
    div {
        width: 100%;
        height: 100%;
    }
</style>
