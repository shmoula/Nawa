# NAWA - Nxt Assets Watcher for Android

A competition entry for [Nxt Hacks 2015 Hackathon](jnxt.org/nxthacks) by shmoula, NXT-EZHL-B8FR-PZHS-DZZBR.


NAWA is a simple Android application with widget, for which you can pick nxt assets, which you want to watch.

Not much time for realization, so I'm going to use Active Android and other dirty stuff, since domain model will be simple, sorry 'bout that O:).

## Screenshots

![](https://raw.githubusercontent.com/shmoula/Nawa/release/Screenshots/device-2015-08-02-110629.png)
![](https://raw.githubusercontent.com/shmoula/Nawa/release/Screenshots/device-2015-08-02-110717.png)
![](https://raw.githubusercontent.com/shmoula/Nawa/release/Screenshots/device-2015-08-02-225012.png)

## Releases

#### Release 0.2 (2015-08-02) [Download .apk](https://www.dropbox.com/s/pn2n6z4maz2bum4/Nawa.apk?dl=0)

- WIDGET displaying watched items among with last trade price compared to previous trade price.
- When 'watching' tapped, trades history for watched asset is downloaded and saved locally.

#### Release 0.1 (2015-08-02)

- Fetches assets list from web and saves locally.
- View asset list in recycler view, sorted by trades count.
- Allow filtering by name.
- Swipe on line reveal button for watching.
- Can watch / unwatch item - watched are sorted first in list.
- Force reload of data from server (which are downloaded only first time).

## TODO

- Upload to Google Play.
- Add some guide for new users.
- Tap on item either on widget or asset list should open asset details.
- Widget should be periodically refreshed with fresh data from web.
- Notifications for specified price change.
- Settings with custom endpoint, refresh interval etc...
- Editable price to count deltas to.
- . . . have some more? Gimme at shmoula@gmail.com